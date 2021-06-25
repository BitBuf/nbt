package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Implementation of the byte array tag.
 *
 * @author dewy
 */
public class ByteArrayTag implements ArrayTag {
    private byte[] value;

    /**
     * Constructs a new byte array tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public ByteArrayTag(byte[] value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of byte array tag cannot be null.");
        }

        this.value = value;
    }

    /**
     * Constructs a new byte array tag with a collection of any primitive numeric type as input.
     *
     * @param value The collection to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public ByteArrayTag(Collection<? extends Number> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of byte array tag cannot be null.");
        }

        Object[] boxedArray = value.toArray();

        int len = boxedArray.length;
        byte[] array = new byte[len];

        for (int i = 0; i < len; i++) {
            array[i] = ((Number) boxedArray[i]).byteValue();
        }

        this.value = array;
    }

    /**
     * Returns the byte array value contained inside the tag.
     *
     * @return The byte array value contained inside the tag.
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Sets the byte array value contained inside the tag.
     *
     * @param value The new value to be contained inside this tag.
     */
    public void setValue(byte[] value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of byte array tag cannot be null.");
        }

        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.BYTE_ARRAY;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.length);
        output.write(this.value);
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteArrayTag that = (ByteArrayTag) o;

        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
