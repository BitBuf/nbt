package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.utils.ReadFunction;
import dev.dewy.nbt.utils.TagType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Implementation of the short tag.
 *
 * @author dewy
 */
public class ShortTag implements NumberTag {
    private short value;

    /**
     * Reads a {@link ShortTag} from a {@link DataInput} stream.
     */
    public static final ReadFunction<DataInput, ShortTag> read = input -> new ShortTag(input.readShort());

    /**
     * Constructs a new short tag with a given value.
     *
     * @param value The value to be contained within the tag.
     */
    public ShortTag(short value) {
        this.value = value;
    }

    /**
     * Returns the short value contained inside the tag.
     *
     * @return The short value contained inside the tag.
     */
    public short getValue() {
        return value;
    }

    /**
     * Sets the short value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(short value) {
        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.SHORT;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeShort(this.value);
    }

    @Override
    public ReadFunction<DataInput, ShortTag> getReader() {
        return read;
    }

    @Override
    public byte getByte() {
        return (byte) this.value;
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

        ShortTag shortTag = (ShortTag) o;

        return value == shortTag.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
