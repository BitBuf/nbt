package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;

/**
 * Implementation of the byte tag.
 *
 * @author dewy
 */
public class ByteTag implements NumberTag {
    private byte value;

    /**
     * Constructs a new byte tag with a given value.
     *
     * @param value The value to be contained within the tag.
     */
    public ByteTag(byte value) {
        this.value = value;
    }

    /**
     * Returns the byte value contained inside the tag.
     *
     * @return The byte value contained inside the tag.
     */
    public byte getValue() {
        return value;
    }

    /**
     * Sets the byte value contained inside the tag.
     *
     * @param value The new value contained inside this tag.
     */
    public void setValue(byte value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.BYTE;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(this.value);
    }

    @Override
    public byte getByte() {
        return this.value;
    }

    @Override
    public short getShort() {
        return this.value;
    }

    @Override
    public int getInt() {
        return this.value;
    }

    @Override
    public long getLong() {
        return this.value;
    }

    @Override
    public float getFloat() {
        return this.value;
    }

    @Override
    public double getDouble() {
        return this.value;
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
