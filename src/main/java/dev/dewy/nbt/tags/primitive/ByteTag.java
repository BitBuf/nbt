package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The byte tag (type ID 1) is used for storing an 8-bit signed two's complement integer; a Java primitive {@code byte}.
 *
 * @author dewy
 */
@NoArgsConstructor
@AllArgsConstructor
public class ByteTag extends NumericalTag<Byte> {
    private byte value;

    /**
     * Constructs a byte tag with a given value.
     *
     * @param value the tag's {@code Number} value, to be converted to {@code byte}.
     */
    public ByteTag(@NonNull Number value) {
        this(null, value);
    }

    /**
     * Constructs a byte tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code Number} value, to be converted to {@code byte}.
     */
    public ByteTag(String name, @NonNull Number value) {
        this(name, value.byteValue());
    }

    /**
     * Constructs a byte tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code byte} value.
     */
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

    /**
     * Sets the {@code byte} value of this byte tag.
     *
     * @param value new {@code byte} value to be set.
     */
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
