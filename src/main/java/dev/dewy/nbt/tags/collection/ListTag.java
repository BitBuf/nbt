package dev.dewy.nbt.tags.collection;

import dev.dewy.nbt.tags.Tag;
import dev.dewy.nbt.TagType;
import dev.dewy.nbt.TagTypeRegistry;
import dev.dewy.nbt.exceptions.TagTypeRegistryException;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ListTag<T extends Tag> extends Tag implements Iterable<T> {
    private List<@NonNull T> value;
    private byte type;

    public ListTag(String name, @NonNull List<@NonNull T> value) {
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

    public byte getType() {
        return this.type;
    }

    public void setValue(List<T> value) {
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
}
