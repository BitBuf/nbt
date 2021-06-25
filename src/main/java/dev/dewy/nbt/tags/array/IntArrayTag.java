package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Implementation of the int array tag.
 *
 * @author dewy
 */
public class IntArrayTag implements ArrayTag {
    private int[] value;

    /**
     * Constructs a new int array tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public IntArrayTag(int[] value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of int array tag cannot be null.");
        }

        this.value = value;
    }

    /**
     * Constructs a new int array tag with a collection of any primitive numeric type as input.
     *
     * @param value The collection to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public IntArrayTag(Collection<? extends Number> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of int array tag cannot be null.");
        }

        Object[] boxedArray = value.toArray();

        int len = boxedArray.length;
        int[] array = new int[len];

        for (int i = 0; i < len; i++) {
            array[i] = ((Number) boxedArray[i]).intValue();
        }

        this.value = array;
    }

    /**
     * Returns the int array value contained inside the tag.
     *
     * @return The int array value contained inside the tag.
     */
    public int[] getValue() {
        return value;
    }

    /**
     * Sets the int array value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(int[] value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of int array tag cannot be null.");
        }

        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.INT_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.length);

        for (int i : this.value) {
            output.writeInt(i);
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

        IntArrayTag that = (IntArrayTag) o;

        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
