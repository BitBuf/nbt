package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class IntTag extends NumericalTag<Integer> {
    private int value;

    public IntTag(String name, int value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getId() {
        return 3;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeInt(this.value);
    }

    @Override
    public IntTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = input.readInt();

        return this;
    }
}
