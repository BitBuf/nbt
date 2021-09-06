package dev.dewy.nbt.tags.collection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.api.json.JsonSerializable;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.registry.TagTypeRegistryException;
import dev.dewy.nbt.api.snbt.SnbtConfig;
import dev.dewy.nbt.api.snbt.SnbtSerializable;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.primitive.*;
import dev.dewy.nbt.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * The compound tag (type ID 10) is used for storing an unordered map of any and all named tags.
 * All tags present in a compound must be given a name (key). Every valid NBT data structure is contained entirely within a "root" compound.
 *
 * @author dewy
 */
@AllArgsConstructor
public class CompoundTag extends Tag implements SnbtSerializable, JsonSerializable, Iterable<Tag> {
    private @NonNull Map<String, Tag> value;

    /**
     * Constructs an empty, unnamed compound tag.
     */
    public CompoundTag() {
        this(null, new LinkedHashMap<>());
    }

    /**
     * Constructs an empty compound tag with a given name.
     *
     * @param name the tag's name.
     */
    public CompoundTag(String name) {
        this(name, new LinkedHashMap<>());
    }

    /**
     * Constructs a compound tag with a given name and {@code Map<>} value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code Map<>} value.
     */
    public CompoundTag(String name, @NonNull Map<String, Tag> value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.COMPOUND.getId();
    }

    @Override
    public Map<String, Tag> getValue() {
        return this.value;
    }

    /**
     * Sets the {@code Map<>} value of this compound tag.
     *
     * @param value new {@code Map<>} value to be set.
     */
    public void setValue(@NonNull Map<String, Tag> value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        for (Tag tag : this) {
            output.writeByte(tag.getTypeId());
            output.writeUTF(tag.getName());

            tag.write(output, depth + 1, registry);
        }

        output.writeByte(0);
    }

    @Override
    public CompoundTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        Map<String, Tag> tags = new LinkedHashMap<>();

        byte nextTypeId;
        Tag nextTag;
        while ((nextTypeId = input.readByte()) != 0) {
            Class<? extends Tag> tagClass = registry.getClassFromId(nextTypeId);

            if (tagClass == null) {
                throw new IOException("Tag type with ID " + nextTypeId + " not present in tag type registry.");
            }

            try {
                nextTag = registry.instantiate(tagClass);
            } catch (TagTypeRegistryException e) {
                throw new IOException(e);
            }

            nextTag.setName(input.readUTF());
            nextTag.read(input, depth + 1, registry);

            tags.put(nextTag.getName(), nextTag);
        }

        this.value = tags;

        return this;
    }

    @Override
    public JsonObject toJson(int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        JsonObject json = new JsonObject();
        JsonObject value = new JsonObject();
        json.addProperty("type", this.getTypeId());

        if (this.getName() != null) {
            json.addProperty("name", this.getName());
        }

        for (Tag tag : this) {
            try {
                value.add(tag.getName(), ((JsonSerializable) tag).toJson(depth + 1, registry));
            } catch (ClassCastException e) {
                throw new IOException("Tag not JsonSerializable.", e);
            }
        }

        json.add("value", value);

        return json;
    }

    @Override
    public CompoundTag fromJson(JsonObject json, int depth, TagTypeRegistry registry) throws IOException {
        if (depth > 512) {
            throw new IOException("NBT structure too complex (depth > 512).");
        }

        this.clear();

        if (json.has("name")) {
            this.setName(json.getAsJsonPrimitive("name").getAsString());
        } else {
            this.setName(null);
        }

        Map<String, Tag> tags = new LinkedHashMap<>();

        byte nextTypeId;
        Tag nextTag;
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject("value").entrySet()) {
            JsonObject entryJson = entry.getValue().getAsJsonObject();

            nextTypeId = entryJson.get("type").getAsByte();
            Class<? extends Tag> tagClass = registry.getClassFromId(nextTypeId);

            if (tagClass == null) {
                throw new IOException("Tag type with ID " + nextTypeId + " not present in tag type registry.");
            }

            try {
                nextTag = registry.instantiate(tagClass);
            } catch (TagTypeRegistryException e) {
                throw new IOException(e);
            }

            ((JsonSerializable) nextTag).fromJson(entryJson, depth + 1, registry);
            tags.put(nextTag.getName(), nextTag);
        }

        this.value = tags;

        return this;
    }

    @Override
    public String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config) {
        if (this.value.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder("{");

        if (config.isPrettyPrint()) {
            sb.append('\n').append(StringUtils.multiplyIndent(depth + 1, config));
        }

        boolean first = true;
        for (Tag tag : this) {
            if (!first) {
                if (config.isPrettyPrint()) {
                    sb.append(",\n").append(StringUtils.multiplyIndent(depth + 1, config));
                } else {
                    sb.append(',');
                }
            }

            sb.append(StringUtils.escapeSnbt(tag.getName()));

            if (config.isPrettyPrint()) {
                sb.append(": ");
            } else {
                sb.append(':');
            }

            sb.append(((SnbtSerializable) tag).toSnbt(depth + 1, registry, config));

            if (first) {
                first = false;
            }
        }

        if (config.isPrettyPrint()) {
            sb.append("\n").append(StringUtils.multiplyIndent(depth , config)).append('}');
        } else {
            sb.append('}');
        }

        return sb.toString();
    }

    /**
     * Returns the number of entries in this compound tag.
     *
     * @return the number of entries in this compound tag.
     */
    public int size() {
        return this.value.size();
    }

    /**
     * Returns true if this compound tag is empty, false otherwise.
     *
     * @return true if this compound tag is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    /**
     * Adds a given tag to this compound. The tag must have a name, or NPE is thrown.
     *
     * @param tag the named tag to be added to the compound.
     * @param <E> the type of an existing tag you believe you may be replacing (optional).
     * @return the previous value mapped with the tag's name as type E if provided, or null if there wasn't any.
     * @throws NullPointerException if the tag's name is null.
     */
    public <E extends Tag> E put(@NonNull Tag tag) {
        return (E) this.value.put(tag.getName(), tag);
    }

    /**
     * Adds a given tag to this compound. Be careful, the tag's name is set to the {@code name} parameter automatically.
     *
     * @param name the tag's name (key).
     * @param tag the tag to be added to the compound.
     * @param <E> the type of an existing tag you believe you may be replacing (optional).
     * @return the previous value mapped with the tag's name as type E if provided, or null if there wasn't any.
     */
    public <E extends Tag> E put(@NonNull String name, @NonNull Tag tag) {
        tag.setName(name);

        return this.put(tag);
    }

    public void putByte(@NonNull String name, byte value) {
        this.put(name, new ByteTag(name, value));
    }

    public void putShort(@NonNull String name, short value) {
        this.put(name, new ShortTag(name, value));
    }

    public void putInt(@NonNull String name, int value) {
        this.put(name, new IntTag(name, value));
    }

    public void putLong(@NonNull String name, long value) {
        this.put(name, new LongTag(name, value));
    }

    public void putFloat(@NonNull String name, float value) {
        this.put(name, new FloatTag(name, value));
    }

    public void putDouble(@NonNull String name, double value) {
        this.put(name, new DoubleTag(name, value));
    }

    public void putByteArray(@NonNull String name, @NonNull byte[] value) {
        this.put(name, new ByteArrayTag(name, value));
    }

    public void putString(@NonNull String name, @NonNull String value) {
        this.put(name, new StringTag(name, value));
    }

    public <T extends Tag> void putList(@NonNull String name, List<T> value) {
        this.put(name, new ListTag<>(name, value));
    }

    public void putCompound(@NonNull String name, @NonNull Map<String, Tag> value) {
        this.put(name, new CompoundTag(name, value));
    }

    public void putIntArray(@NonNull String name, @NonNull int[] value) {
        this.put(name, new IntArrayTag(name, value));
    }

    public void putLongArray(@NonNull String name, @NonNull long[] value) {
        this.put(name, new LongArrayTag(name, value));
    }

    /**
     * Removes a tag from this compound with a given name (key).
     *
     * @param key the name whose mapping is to be removed from this compound.
     * @param <T> the tag type you believe you are removing (optional).
     * @return the previous value associated with {@code key} as type T if provided.
     */
    public <T extends Tag> T remove(@NonNull String key) {
        return (T) this.value.remove(key);
    }

    /**
     * Retrieves a tag from this compound with a given name (key).
     *
     * @param key the name whose mapping is to be retrieved from this compound.
     * @param <T> the tag type you believe you are retrieving.
     * @return the value associated with {@code key} as type T.
     */
    public <T extends Tag> T get(@NonNull String key) {
        return (T) this.value.get(key);
    }

    public ByteTag getByte(@NonNull String key) {
        return this.get(key);
    }

    public ShortTag getShort(@NonNull String key) {
        return this.get(key);
    }

    public IntTag getInt(@NonNull String key) {
        return this.get(key);
    }

    public LongTag getLong(@NonNull String key) {
        return this.get(key);
    }

    public FloatTag getFloat(@NonNull String key) {
        return this.get(key);
    }

    public DoubleTag getDouble(@NonNull String key) {
        return this.get(key);
    }

    public ByteArrayTag getByteArray(@NonNull String key) {
        return this.get(key);
    }

    public StringTag getString(@NonNull String key) {
        return this.get(key);
    }

    public <T extends Tag> ListTag<T> getList(@NonNull String key) {
        return this.get(key);
    }

    public CompoundTag getCompound(@NonNull String key) {
        return this.get(key);
    }

    public IntArrayTag getIntArray(@NonNull String key) {
        return this.get(key);
    }

    public LongArrayTag getLongArray(@NonNull String key) {
        return this.get(key);
    }

    /**
     * Returns true if this compound contains an entry with a given name (key), false otherwise.
     *
     * @param key the name (key) to check for.
     * @return true if this compound contains an entry with a given name (key), false otherwise.
     */
    public boolean contains(@NonNull String key) {
        return this.value.containsKey(key);
    }

    /**
     * Returns true if this compound contains an entry with a given name (key) and if that entry is of a given tag type, false otherwise.
     *
     * @param key the name (key) to check for.
     * @param typeId the tag type ID to test for.
     * @return true if this compound contains an entry with a given name (key) and if that entry is of a given tag type, false otherwise.
     */
    public boolean contains(@NonNull String key, byte typeId) {
        if (!this.contains(key)) {
            return false;
        }

        return this.get(key).getTypeId() == typeId;
    }

    public boolean containsByte(@NonNull String key) {
        return this.contains(key, TagType.BYTE.getId());
    }

    public boolean containsShort(@NonNull String key) {
        return this.contains(key, TagType.SHORT.getId());
    }

    public boolean containsInt(@NonNull String key) {
        return this.contains(key, TagType.INT.getId());
    }

    public boolean containsLong(@NonNull String key) {
        return this.contains(key, TagType.LONG.getId());
    }

    public boolean containsFloat(@NonNull String key) {
        return this.contains(key, TagType.FLOAT.getId());
    }

    public boolean containsDouble(@NonNull String key) {
        return this.contains(key, TagType.DOUBLE.getId());
    }

    public boolean containsByteArray(@NonNull String key) {
        return this.contains(key, TagType.BYTE_ARRAY.getId());
    }

    public boolean containsString(@NonNull String key) {
        return this.contains(key, TagType.STRING.getId());
    }

    public boolean containsList(@NonNull String key) {
        return this.contains(key, TagType.LIST.getId());
    }

    public boolean containsListOf(@NonNull String key, byte of) {
        return this.containsList(key) && this.getList(key).getListType() == of;
    }

    public boolean containsCompound(@NonNull String key) {
        return this.contains(key, TagType.COMPOUND.getId());
    }

    public boolean containsIntArray(@NonNull String key) {
        return this.contains(key, TagType.INT_ARRAY.getId());
    }

    public boolean containsLongArray(@NonNull String key) {
        return this.contains(key, TagType.LONG_ARRAY.getId());
    }

    /**
     * Returns all {@link Tag}s contained within this compound.
     *
     * @return all {@link Tag}s contained within this compound.
     */
    public Collection<Tag> values() {
        return this.value.values();
    }

    /**
     * Returns a {@code Set<>} of all names (keys) currently used within this compound.
     *
     * @return a {@code Set<>} of all names (keys) currently used within this compound.
     */
    public Set<String> keySet() {
        return this.value.keySet();
    }

    /**
     * Removes all entries from the compound. The compound will be empty after this call returns.
     */
    public void clear() {
        this.value.clear();
    }

    @Override
    public Iterator<Tag> iterator() {
        return this.value.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super Tag> action) {
        this.value.values().forEach(action);
    }

    @Override
    public Spliterator<Tag> spliterator() {
        return this.value.values().spliterator();
    }

    @Override
    public String toString() {
        return this.toSnbt(0, new TagTypeRegistry(), new SnbtConfig());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundTag that = (CompoundTag) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
