package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.TagRegistry;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteArrayTag extends ArrayTag<Byte> {
    private @NonNull byte[] value;

    public ByteArrayTag(String name, @NonNull byte[] value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getId() {
        return 7;
    }

    @Override
    public byte[] getValue() {
        return this.value;
    }

    public void setValue(@NonNull byte[] value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeInt(this.value.length);
        output.write(this.value);
    }

    @Override
    public ByteArrayTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        byte[] tmp = new byte[input.readInt()];
        input.readFully(tmp);

        this.value = tmp;

        return this;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public Byte get(int index) {
        return this.value[index];
    }

    @Override
    public Byte set(int index, @NonNull Byte newValue) {
        return this.value[index] = newValue;
    }

    @Override
    public void insert(int index, @NonNull Byte... values) {
        this.value = ArrayUtils.insert(index, this.value, ArrayUtils.toPrimitive(values));
    }

    @Override
    public Byte remove(int index) {
        Byte previous = this.value[index];
        this.value = ArrayUtils.remove(this.value, index);

        return previous;
    }

    @Override
    public void clear() {
        this.value = new byte[0];
    }
}
