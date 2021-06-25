package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;

/**
 * Implementation of the int tag.
 *
 * @author dewy
 */
public class IntTag implements NumberTag {
    private int value;

    /**
     * Constructs a new int tag with a given value.
     *
     * @param value The value to be contained within the tag.
     */
    public IntTag(int value) {
        this.value = value;
    }

    /**
     * Returns the int value contained inside the tag.
     *
     * @return The int value contained inside the tag.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the int value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.INT;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value);
    }

    @Override
    public byte getByte() {
        return (byte) this.value;
    }

    @Override
    public short getShort() {
        return (short) this.value;
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

        IntTag intTag = (IntTag) o;

        return value == intTag.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
