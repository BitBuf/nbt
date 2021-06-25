package dev.dewy.nbt.tags;

import dev.dewy.nbt.Tag;
import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

/**
 * Implementation of the compound tag. A map in its raw form.
 *
 * @author dewy
 */
public class CompoundTag implements Tag {
    private Map<String, Tag> value;

    /**
     * Constructs a new compound tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public CompoundTag(Map<String, Tag> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of compound tag cannot be null.");
        }

        this.value = value;
    }

    /**
     * Returns the map value contained inside the tag.
     *
     * @return The map value contained inside the tag.
     */
    public Map<String, Tag> getValue() {
        return value;
    }

    /**
     * Sets the map value contained inside the tag.
     *
     * @param value The new map value to be contained inside this tag.
     */
    public void setValue(Map<String, Tag> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of compound tag cannot be null.");
        }

        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.COMPOUND;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        for (Map.Entry<String, Tag> tag : this.value.entrySet()) {
            output.writeByte(tag.getValue().getType().getId());

            if (tag.getValue().getType() != TagType.END) {
                output.writeUTF(tag.getKey());
                tag.getValue().write(output);
            }
        }

        output.writeByte(TagType.END.getId()); // 0x00
    }

    /**
     * Write the compound tag to a {@link DataOutput} stream as the root compound with no name of its own.
     *
     * @param output The stream to write to.
     * @param root Whether or not to write the tag as the root compound.
     * @throws IOException If any IO error occurs.
     */
    public void write(DataOutput output, boolean root) throws IOException {
        if (root) {
            write(output, "");
        } else {
            write(output);
        }
    }

    /**
     * Write the compound tag to a {@link DataOutput} stream as the root compound with a name of its own.
     *
     * @param output The stream to write to.
     * @param rootName The root compound's name.
     * @throws IOException If any IO error occurs.
     */
    public void write(DataOutput output, String rootName) throws IOException {
        output.writeByte(TagType.COMPOUND.getId());
        output.writeUTF(rootName);

        write(output);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundTag that = (CompoundTag) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
