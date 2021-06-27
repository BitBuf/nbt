package dev.dewy.nbt.tags;

import dev.dewy.nbt.Tag;
import dev.dewy.nbt.TagType;
import dev.dewy.nbt.utils.ReadFunction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Implementation of the list tag.
 *
 * @author dewy
 */
public class ListTag<T extends Tag> implements Tag, Iterable<T> {
    private List<T> value;

    /**
     * Reads a {@link ListTag} from a {@link DataInput} stream.
     */
    public static final ReadFunction<DataInput, ListTag<? extends Tag>> read = input -> {
        List<Tag> tags = new ArrayList<>();
        TagType type = TagType.fromByte(input.readByte());

        int length = input.readInt();

        for (int i = 1; i <= length; i++) {
            tags.add(ReadFunction.of(type).read(input));
        }

        for (Tag tag : tags) {
            if (tag.getType() != type) {
                throw new IOException("List tag must only contain the type of tag specified: " + type.getName());
            }
        }

        return new ListTag<>(tags);
    };

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
    public ListTag(List<T> value) {
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
    public List<T> getValue() {
        return value;
    }

    /**
     * Sets the list value contained inside the tag.
     *
     * @param value The new list value to be contained inside this tag.
     */
    public void setValue(List<T> value) {
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

        for (T tag : this.value) {
            tag.write(output);
        }
    }

    @Override
    public ReadFunction<DataInput, ListTag<? extends Tag>> getReader() {
        return read;
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
     * Appends the specified tag to the end of this list tag.
     *
     * @param tag The tag to append.
     * @return True, always.
     */
    public boolean add(T tag) {
        return this.value.add(tag);
    }

    /**
     * Inserts the specified tag at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param tag The tag to be inserted.
     */
    public void add(int index, T tag) {
        this.value.add(index, tag);
    }

    /**
     * Removes the first occurrence of the specified tag from this list, if it is present.
     *
     * @param tag The tag to remove.
     * @return True if the list contained the specified tag.
     */
    public boolean remove(T tag) {
        return this.value.remove(tag);
    }

    /**
     * Removes the tag at the specified position in this list.
     *
     * @param index The index of the tag to be removed.
     * @return The tag previously at the specified position.
     */
    public T remove(int index) {
        return this.value.remove(index);
    }

    /**
     * Returns the tag at the specified position in this list.
     *
     * @param index Index of the tag to return.
     * @return The tag at the specified position in this list.
     */
    public T get(int index) {
        return this.value.get(index);
    }

    /**
     * Returns true if this list inside this tag contains the specified tag.
     *
     * @param tag Tag whose presence is to be tested.
     * @return True if this list inside this tag contains the specified tag.
     */
    public boolean contains(T tag) {
        return this.value.contains(tag);
    }

    /**
     * Returns the index of the first occurrence of the specified tag in this list, or -1 if this list does not contain the tag.

     * @param tag Tag to search for.
     * @return The index of the first occurrence of the specified tag in this list, or -1 if this list does not contain the tag.
     */
    public int indexOf(T tag) {
        return this.value.indexOf(tag);
    }

    @Override
    public Iterator<T> iterator() {
        return this.value.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.value.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.value.spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListTag<?> listTag = (ListTag<?>) o;

        return value.equals(listTag.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
