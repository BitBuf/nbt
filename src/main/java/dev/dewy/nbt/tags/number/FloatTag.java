package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;

/**
 * Implementation of the float tag.
 *
 * @author dewy
 */
public class FloatTag implements NumberTag {
    private float value;

    /**
     * Constructs a new float tag with a given value.
     *
     * @param value The value to be contained within the tag.
     */
    public FloatTag(float value) {
        this.value = value;
    }

    /**
     * Returns the float value contained inside the tag.
     *
     * @return The float value contained inside the tag.
     */
    public float getValue() {
        return value;
    }

    /**
     * Sets the float value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.FLOAT;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeFloat(this.value);
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
        return (int) this.value;
    }

    @Override
    public long getLong() {
        return (long) this.value;
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

        FloatTag floatTag = (FloatTag) o;

        return Float.compare(floatTag.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return (value != +0.0f ? Float.floatToIntBits(value) : 0);
    }
}
