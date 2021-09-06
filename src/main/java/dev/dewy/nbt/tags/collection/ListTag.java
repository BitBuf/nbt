package dev.dewy.nbt.tags.collection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.api.json.JsonSerializable;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.registry.TagTypeRegistryException;
import dev.dewy.nbt.api.snbt.SnbtConfig;
import dev.dewy.nbt.api.snbt.SnbtSerializable;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * The list tag (type ID 9) is used for storing an ordered list of unnamed NBT tags all of the same type.
 *
 * @author dewy
 */
@AllArgsConstructor
public class ListTag<T extends Tag> extends Tag implements SnbtSerializable, JsonSerializable, Iterable<T> {
    private @NonNull List<T> value;
    private byte type;

    /**
     * Constructs an empty, unnamed list tag.
     */
    public ListTag() {
        this(null);
    }

    /**
     * Constructs an empty list tag with a given name.
     *
     * @param name the tag's name.
     */
    public ListTag(String name) {
        this(name, new LinkedList<>());
    }

    /**
     * Constructs a list tag with a given name and {@code List<>} value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code List<>} value.
     */
    public ListTag(String name, @NonNull List<T> value) {
        if (value.isEmpty()) {
            this.type = 0;
        } else {
            this.type = value.get(0).getTypeId();
        }

        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.LIST.getId();
    }

    @Override
    public List<T> getValue() {
        return this.value;
    }

    /**
     * Returns the ID of the NBT tag type this list holds.
     *
     * @return the ID of the NBT tag type this list holds.
     */
    public byte getListType() {
        return this.type;
    }

    /**
     * Sets the {@code List<>} value of this list tag.
     *
     * @param value new {@code List<>} value to be set.
     */
    public void setValue(@NonNull List<T> value) {
        if (value.isEmpty()) {
            this.type = 0;
        } else {
            this.type = value.get(0).getTypeId();
        }

        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        output.writeByte(this.type);
        output.writeInt(this.value.size());

        for (T tag : this) {
            tag.write(output, depth + 1, registry);
        }
    }

    @Override
    public ListTag<T> read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        List<T> tags = new ArrayList<>();

        byte tagType = input.readByte();
        int length = input.readInt();

        T next;
        for (int i = 0; i < length; i++) {
            Class<? extends Tag> tagClass = registry.getClassFromId(tagType);

            if (tagClass == null) {
                throw new IOException("Tag type with ID " + tagType + " not present in tag type registry.");
            }

            try {
                next = (T) registry.instantiate(tagClass);
            } catch (TagTypeRegistryException e) {
                throw new IOException(e);
            }

            next.read(input, depth + 1, registry);
            next.setName(null);

            tags.add(next);
        }

        if (tags.isEmpty()) {
            this.type = 0;
        } else {
            this.type = tagType;
        }

        this.value = tags;

        return this;
    }

    @Override
    public String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config) {
        StringBuilder sb = new StringBuilder("[");

        if (config.isPrettyPrint()) {
            sb.append('\n').append(StringUtils.multiplyIndent(depth + 1, config));
        }

        for (int i = 0; i < this.value.size(); ++i) {
            if (i != 0) {
                if (config.isPrettyPrint()) {
                    sb.append(",\n").append(StringUtils.multiplyIndent(depth + 1, config));
                } else {
                    sb.append(',');
                }
            }

            sb.append(((SnbtSerializable) this.value.get(i)).toSnbt(depth + 1, registry, config));
        }

        if (config.isPrettyPrint()) {
            sb.append("\n").append(StringUtils.multiplyIndent(depth , config)).append(']');
        } else {
            sb.append(']');
        }

        return sb.toString();
    }

    @Override
    public JsonObject toJson(int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        JsonObject json = new JsonObject();
        JsonArray value = new JsonArray();

        json.addProperty("type", this.getTypeId());
        json.addProperty("listType", this.getListType());

        if (this.getName() != null) {
            json.addProperty("name", this.getName());
        }

        for (T tag : this) {
            tag.setName(null);
            value.add(((JsonSerializable) tag).toJson(depth + 1, registry));
        }

        json.add("value", value);

        return json;
    }

    @Override
    public ListTag<T> fromJson(JsonObject json, int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        this.clear();

        if (json.has("name")) {
            this.setName(json.getAsJsonPrimitive("name").getAsString());
        } else {
            this.setName(null);
        }

        byte listType = json.get("listType").getAsByte();
        List<T> tags = new LinkedList<>();

        T nextTag;
        for (JsonElement element : json.getAsJsonArray("value")) {
            Class<? extends Tag> tagClass = registry.getClassFromId(listType);

            if (tagClass == null) {
                throw new IOException("Tag type with ID " + listType + " not present in tag type registry.");
            }

            try {
                nextTag = (T) registry.instantiate(tagClass);
            } catch (TagTypeRegistryException e) {
                throw new IOException(e);
            }

            ((JsonSerializable) nextTag).fromJson((JsonObject) element, depth + 1, registry);
            tags.add(nextTag);
        }

        if (tags.isEmpty()) {
            this.type = 0;
        } else {
            this.type = listType;
        }

        this.value = tags;

        return this;
    }

    /**
     * Returns the number of elements in this list tag.
     *
     * @return the number of elements in this list tag.
     */
    public int size() {
        return this.value.size();
    }

    /**
     * Returns true if this list tag is empty, false otherwise.
     *
     * @return true if this list tag is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    /**
     * Appends the specified tag to the end of the list. Returns true if added successfully.
     *
     * @param tag the tag to be added.
     * @return true if added successfully.
     */
    public boolean add(@NonNull T tag) {
        if (this.value.isEmpty()) {
            this.type = tag.getTypeId();
        }

        if (tag.getTypeId() != this.type) {
            return false;
        }

        return this.value.add(tag);
    }

    /**
     * Inserts the specified tag at the specified position in this list.
     * Shifts the tag currently at that position and any subsequent tags to the right.
     *
     * @param index index at which the tag is to be inserted.
     * @param tag tag to be inserted.
     */
    public void insert(int index, @NonNull T tag) {
        if (this.value.isEmpty()) {
            this.type = tag.getTypeId();
        }

        if (tag.getTypeId() != this.type) {
            return;
        }

        this.value.add(index, tag);
    }

    /**
     * Removes a given tag from the list. Returns true if removed successfully, false otherwise.
     *
     * @param tag the tag to be removed.
     * @return true if the tag was removed successfully, false otherwise.
     */
    public boolean remove(@NonNull T tag) {
        boolean success = this.value.remove(tag);

        if (this.value.isEmpty()) {
            this.type = 0;
        }

        return success;
    }

    /**
     * Removes a tag from the list based on the tag's index. Returns the removed tag.
     *
     * @param index the index of the tag to be removed.
     * @return the removed tag.
     */
    public T remove(int index) {
        T previous = this.value.remove(index);

        if (this.value.isEmpty()) {
            this.type = 0;
        }

        return previous;
    }

    /**
     * Retrieves a tag from its index in the list.
     *
     * @param index the index of the tag to be retrieved.
     * @return the tag at the specified index.
     */
    public T get(int index) {
        return this.value.get(index);
    }

    /**
     * Returns true if this list contains the tag, false otherwise.
     *
     * @param tag the tag to check for.
     * @return true if this list contains the tag, false otherwise.
     */
    public boolean contains(@NonNull T tag) {
        return this.value.contains(tag);
    }

    /**
     * Returns true if this list contains all tags in the collection, false otherwise.
     *
     * @param tags the tags to be checked for.
     * @return true if this list contains all tags in the collection, false otherwise.
     */
    public boolean containsAll(@NonNull Collection<T> tags) {
        return this.value.containsAll(tags);
    }

    /**
     * Removes all tags from the list. The list will be empty after this call returns.
     */
    public void clear() {
        this.type = 0;
        this.value.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.value.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.value.spliterator();
    }

    @Override
    public String toString() {
        return this.toSnbt(0, new TagTypeRegistry(), new SnbtConfig());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListTag<?> listTag = (ListTag<?>) o;

        if (type != listTag.type) return false;
        return Objects.equals(value, listTag.value);
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (int) type;
        return result;
    }
}
