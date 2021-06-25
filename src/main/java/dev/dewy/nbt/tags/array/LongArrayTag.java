package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Implementation of the long array tag.
 *
 * @author dewy
 */
public class LongArrayTag implements ArrayTag {
    private long[] value;

    /**
     * Constructs a new long array tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public LongArrayTag(long[] value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of long array tag cannot be null.");
        }

        this.value = value;
    }

    /**
     * Constructs a new long array tag with a collection of any primitive numeric type as input.
     *
     * @param value The collection to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public LongArrayTag(Collection<? extends Number> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of long array tag cannot be null.");
        }

        Object[] boxedArray = value.toArray();

        int len = boxedArray.length;
        long[] array = new long[len];

        for (int i = 0; i < len; i++) {
            array[i] = ((Number) boxedArray[i]).longValue();
        }

        this.value = array;
    }

    /**
     * Returns the long array value contained inside the tag.
     *
     * @return The long array value contained inside the tag.
     */
    public long[] getValue() {
        return value;
    }

    /**
     * Sets the long array value contained inside the tag.
     *
     * @param value The new value contained inside this tag.
     */
    public void setValue(long[] value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of long array tag cannot be null.");
        }

        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.LONG_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.length);

        for (long l : this.value) {
            output.writeLong(l);
        }
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongArrayTag that = (LongArrayTag) o;

        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
