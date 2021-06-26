package dev.dewy.nbt.tags;

import dev.dewy.nbt.Tag;
import dev.dewy.nbt.TagType;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the list tag.
 *
 * @author dewy
 */
public class ListTag implements Tag {
    private List<? extends Tag> value;

    /**
     * Constructs a new empty list tag.
     */
    public ListTag() {
        this.value = new ArrayList<>();
    }

    /**
     * Constructs a new list tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public ListTag(List<? extends Tag> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of list tag cannot be null.");
        }

        this.value = value;
    }

    /**
     * Returns the list value contained inside the tag.
     *
     * @return The list value contained inside the tag.
     */
    public List<? extends Tag> getValue() {
        return value;
    }

    /**
     * Sets the list value contained inside the tag.
     *
     * @param value The new list value to be contained inside this tag.
     */
    public void setValue(List<? extends Tag> value) {
        if (value == null) {
            throw new IllegalArgumentException("Value of list tag cannot be null.");
        }

        this.value = value;
    }

    @Override
    public TagType getType() {
        return TagType.LIST;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        TagType type;

        if (!this.value.isEmpty()) {
            type = this.value.get(0).getType();
        } else {
            type = TagType.END;
        }

        output.writeByte(type.getId());
        output.writeInt(this.value.size());

        for (Tag tag : this.value) {
            tag.write(output);
        }
    }

    /**
     * Returns the length of the list inside this tag.
     *
     * @return The length of the list inside this tag.
     */
    public int size() {
        return this.value.size();
    }

    /**
     * Returns true if the list inside this tag is empty.
     *
     * @return True if the list inside this tag is empty (size() is 0).
     */
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    /**
     * Returns the tag at the specified position in this list.
     *
     * @param index Index of the tag to return.
     * @return The tag at the specified position in this list.
     */
    public Tag get(int index) {
        return this.value.get(index);
    }

    /**
     * Returns true if this list inside this tag contains the specified tag.
     *
     * @param tag Tag whose presence is to be tested.
     * @return True if this list inside this tag contains the specified tag.
     */
    public boolean contains(Tag tag) {
        return this.value.contains(tag);
    }

    /**
     * Returns the index of the first occurrence of the specified tag in this list, or -1 if this list does not contain the tag.

     * @param tag Tag to search for.
     * @return The index of the first occurrence of the specified tag in this list, or -1 if this list does not contain the tag.
     */
    public int indexOf(Tag tag) {
        return this.value.indexOf(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListTag listTag = (ListTag) o;

        return value.equals(listTag.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
