package dev.dewy.nbt.tags.collection;

import dev.dewy.nbt.tags.Tag;
import dev.dewy.nbt.TagType;
import dev.dewy.nbt.TagTypeRegistry;
import dev.dewy.nbt.exceptions.TagTypeRegistryException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
public class CompoundTag extends Tag implements Iterable<Tag> {
    private Map<@NonNull String, @NonNull Tag> value;

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

    public int size() {
        return this.value.size();
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public <T extends Tag> T put(@NonNull T tag) {
        return (T) this.value.put(tag.getName(), tag);
    }

    public <T extends Tag> T remove(@NonNull String key) {
        return (T) this.value.remove(key);
    }

    public <T extends Tag> T get(@NonNull String key) {
        return (T) this.value.get(key);
    }

    public boolean contains(@NonNull String key) {
        return this.value.containsKey(key);
    }

    public Collection<Tag> values() {
        return this.value.values();
    }

    public Set<String> keySet() {
        return this.value.keySet();
    }

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
}
