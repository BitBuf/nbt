package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class ByteTag extends NumericalTag<Byte> {
    private byte value;

    public ByteTag(String name, byte value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getId() {
        return 1;
    }

    @Override
    public Byte getValue() {
        return this.value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeByte(this.value);
    }

    @Override
    public ByteTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = input.readByte();

        return this;
    }
}
