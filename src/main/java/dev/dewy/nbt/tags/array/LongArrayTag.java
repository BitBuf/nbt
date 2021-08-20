package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.TagRegistry;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongArrayTag extends ArrayTag<Long> {
    private @NonNull long[] value;

    public LongArrayTag(String name, @NonNull long[] value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getId() {
        return 12;
    }

    @Override
    public long[] getValue() {
        return this.value;
    }

    public void setValue(@NonNull long[] value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeInt(this.value.length);

        for (long l : this.value) {
            output.writeLong(l);
        }
    }

    @Override
    public LongArrayTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = new long[input.readInt()];

        for (int i = 0; i < this.value.length; i++) {
            this.value[i] = input.readLong();
        }

        return this;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public Long get(int index) {
        return this.value[index];
    }

    @Override
    public Long set(int index, @NonNull Long newValue) {
        return this.value[index] = newValue;
    }

    @Override
    public void insert(int index, @NonNull Long... values) {
        this.value = ArrayUtils.insert(index, this.value, ArrayUtils.toPrimitive(values));
    }

    @Override
    public Long remove(int index) {
        Long previous = this.value[index];
        this.value = ArrayUtils.remove(this.value, index);

        return previous;
    }

    @Override
    public void clear() {
        this.value = new long[0];
    }
}
