package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagRegistry;
import dev.dewy.nbt.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class ShortTag extends NumericalTag<Short> {
    private short value;

    public ShortTag(String name, short value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.SHORT.getId();
    }

    @Override
    public Short getValue() {
        return this.value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeShort(this.value);
    }

    @Override
    public ShortTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = input.readShort();

        return this;
    }
}
