package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class LongTag extends NumericalTag<Long> {
    private long value;

    public LongTag(String name, long value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getId() {
        return 4;
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeLong(this.value);
    }

    @Override
    public LongTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = input.readLong();

        return this;
    }
}
