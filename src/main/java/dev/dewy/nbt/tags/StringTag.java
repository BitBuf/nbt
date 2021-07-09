package dev.dewy.nbt.tags;

import dev.dewy.nbt.utils.ReadFunction;
import dev.dewy.nbt.utils.TagType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of the string tag.
 *
 * @author dewy
 */
public class StringTag implements Tag {
    private String value;

    /**
     * Reads a {@link StringTag} from a {@link DataInput} stream.
     */
    public static final ReadFunction<DataInput, StringTag> read = input -> new StringTag(input.readUTF());

    /**
     * Constructs a new string tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public StringTag(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of string tag cannot be null.");
        }

        this.value = value;
    }

    /**
     * Returns the string value contained inside the tag.
     *
     * @return The string value contained inside the tag.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the string value contained inside the tag.
     *
     * @param value The new string value to be contained inside this tag.
     */
    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of string tag cannot be null.");
        }

        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.STRING;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(this.value);
    }

    @Override
    public ReadFunction<DataInput, StringTag> getReader() {
        return read;
    }

    /**
     * Returns the length of the string inside this tag.
     *
     * @return The length of the string inside this tag.
     */
    public int length() {
        return this.value.length();
    }

    /**
     * Returns true if the string inside this tag is empty.
     *
     * @return True if the string inside this tag is empty (length() is 0).
     */
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    /**
     * Tells whether or not the string inside this tag matches the given regular expression.
     *
     * @param regex The regular expression to which this string is to be matched.
     * @return True if the string inside this tag matches the given regular expression, false otherwise.
     */
    public boolean matches(String regex) {
        return this.value.matches(regex);
    }

    /**
     * Returns the string inside this tag into a sequence of bytes using the UTF-8 charset, storing the result into a new byte array.
     *
     * @return The string inside this tag encoded as a UTF-8 byte array.
     */
    public byte[] getBytes() {
        return this.value.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringTag stringTag = (StringTag) o;

        return value.equals(stringTag.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
