package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagType;
import dev.dewy.nbt.TagTypeRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class ByteTag extends NumericalTag<Byte> {
    private byte value;

    public ByteTag(@NonNull Number value) {
        this(null, value);
    }

    public ByteTag(String name, @NonNull Number value) {
        this(name, value.byteValue());
    }

    public ByteTag(String name, byte value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.BYTE.getId();
    }

    @Override
    public Byte getValue() {
        return this.value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeByte(this.value);
    }

    @Override
    public ByteTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = input.readByte();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteTag byteTag = (ByteTag) o;

        return value == byteTag.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
