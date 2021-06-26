package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.TagType;
import dev.dewy.nbt.utils.ReadFunction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Implementation of the double tag.
 *
 * @author dewy
 */
public class DoubleTag implements NumberTag {
    private double value;

    /**
     * Reads a {@link DoubleTag} from a {@link DataInput} stream.
     */
    public static final ReadFunction<DataInput, DoubleTag> read = input -> new DoubleTag(input.readDouble());

    /**
     * Constructs a new double tag with a given value.
     *
     * @param value The value to be contained within the tag.
     */
    public DoubleTag(double value) {
        this.value = value;
    }

    /**
     * Returns the double value contained inside the tag.
     *
     * @return The double value contained inside the tag.
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the double value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.DOUBLE;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeDouble(this.value);
    }

    @Override
    public ReadFunction<DataInput, DoubleTag> getReader() {
        return read;
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
        return (float) this.value;
    }

    @Override
    public double getDouble() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoubleTag doubleTag = (DoubleTag) o;

        return Double.compare(doubleTag.value, value) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
