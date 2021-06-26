package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.TagType;
import dev.dewy.nbt.utils.ReadFunction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Implementation of the long tag.
 *
 * @author dewy
 */
public class LongTag implements NumberTag {
    private long value;

    /**
     * Reads a {@link LongTag} from a {@link DataInput} stream.
     */
    public static final ReadFunction<DataInput, LongTag> read = input -> new LongTag(input.readLong());

    /**
     * Constructs a new long tag with a given value.
     *
     * @param value The value to be contained within the tag.
     */
    public LongTag(long value) {
        this.value = value;
    }

    /**
     * Returns the long value contained inside the tag.
     *
     * @return The long value contained inside the tag.
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the long value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.LONG;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeLong(this.value);
    }

    @Override
    public ReadFunction<DataInput, LongTag> getReader() {
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

        LongTag longTag = (LongTag) o;

        return value == longTag.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
