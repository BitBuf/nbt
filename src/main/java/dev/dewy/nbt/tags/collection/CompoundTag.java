package dev.dewy.nbt.tags.collection;

import dev.dewy.nbt.TagType;
import dev.dewy.nbt.TagTypeRegistry;
import dev.dewy.nbt.exceptions.TagTypeRegistryException;
import dev.dewy.nbt.tags.Tag;
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
public class CompoundTag extends Tag implements Iterable<Tag> {
    private @NonNull Map<@NonNull String, @NonNull Tag> value;

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
    public CompoundTag(String name, @NonNull Map<@NonNull String, @NonNull Tag> value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.COMPOUND.getId();
    }

    @Override
    public Map<@NonNull String, @NonNull Tag> getValue() {
        return this.value;
    }

    /**
     * Sets the {@code Map<>} value of this compound tag.
     *
     * @param value new {@code Map<>} value to be set.
     */
    public void setValue(@NonNull Map<@NonNull String, @NonNull Tag> value) {
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

        Map<@NonNull String, @NonNull Tag> tags = new LinkedHashMap<>();

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
     * @param <T> the tag type you believe you may be replacing (optional).
     * @return the previous value mapped with the tag's name as type T if provided, or null if there wasn't any.
     * @throws NullPointerException if the tag's name is null.
     */
    public <T extends Tag> T put(@NonNull Tag tag) {
        return (T) this.value.put(tag.getName(), tag);
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
