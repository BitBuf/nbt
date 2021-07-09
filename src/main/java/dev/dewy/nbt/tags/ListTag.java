package dev.dewy.nbt.tags;

import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.number.*;
import dev.dewy.nbt.utils.ReadFunction;
import dev.dewy.nbt.utils.TagType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Implementation of the list tag.
 *
 * @author dewy
 */
public class ListTag<T extends Tag> extends AbstractList<T> implements Tag, List<T>, RandomAccess, Cloneable {
    private final List<T> value;
    private TagType type;

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

        return new ListTag<>(tags, type, false);
    };

    /**
     * Constructs a new empty list tag.
     *
     * @param type The type of the items to be contained in the list.
     */
    public ListTag(TagType type) {
        this(new ArrayList<>(), type);
    }

    /**
     * Constructs a new list tag with a given value.
     *
     * @param value The value to be contained within the tag.
     * @param type The type of the items to be contained in the list.
     * @throws IllegalArgumentException If the value parameter is null.
     */
    public ListTag(List<T> value, TagType type) {
        this(value, type, true);
    }

    private ListTag(List<T> value, TagType type, boolean manualInit) {
        if (value == null || type == null) {
            throw new IllegalArgumentException("Value / type of list tag cannot be null.");
        }

        if (type == TagType.END && manualInit) {
            throw new IllegalArgumentException("Type of list cannot be END.");
        }

        this.value = value;
        this.type = type;
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
     * Returns the tag type of the list's items.
     *
     * @return The tag type of the list's items.
     */
    public TagType getListType() {
        return this.type;
    }

    @Override
    public TagType getType() {
        return TagType.LIST;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        TagType type = this.type;

        if (this.value.isEmpty()) {
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
        if (this.isEmpty()) {
            this.type = tag.getType();
        }

        return this.value.add(tag);
    }

    /**
     * Inserts the specified tag at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param tag The tag to be inserted.
     */
    public void add(int index, T tag) {
        if (this.isEmpty()) {
            this.type = tag.getType();
        }

        this.value.add(index, tag);
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addByte(byte value) {
        verifyType(TagType.BYTE);
        return this.add((T) new ByteTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addByte(int index, byte value) {
        verifyType(TagType.BYTE);

        this.add(index, (T) new ByteTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addShort(short value) {
        verifyType(TagType.SHORT);
        return this.add((T) new ShortTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addShort(int index, short value) {
        verifyType(TagType.SHORT);
        this.add(index, (T) new ShortTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addInt(int value) {
        verifyType(TagType.INT);
        return this.add((T) new IntTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addInt(int index, int value) {
        verifyType(TagType.INT);
        this.add(index, (T) new IntTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addLong(long value) {
        verifyType(TagType.LONG);
        return this.add((T) new LongTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addLong(int index, long value) {
        verifyType(TagType.LONG);
        this.add(index, (T) new LongTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addFloat(float value) {
        verifyType(TagType.FLOAT);
        return this.add((T) new FloatTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addFloat(int index, float value) {
        verifyType(TagType.FLOAT);
        this.add(index, (T) new FloatTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addDouble(double value) {
        verifyType(TagType.DOUBLE);
        return this.add((T) new DoubleTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addDouble(int index, double value) {
        verifyType(TagType.DOUBLE);
        this.add(index, (T) new DoubleTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addByteArray(byte[] value) {
        verifyType(TagType.BYTE_ARRAY);
        return this.add((T) new ByteArrayTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addByteArray(int index, byte[] value) {
        verifyType(TagType.BYTE_ARRAY);
        this.add(index, (T) new ByteArrayTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addIntArray(int[] value) {
        verifyType(TagType.INT_ARRAY);
        return this.add((T) new IntArrayTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addIntArray(int index, int[] value) {
        verifyType(TagType.INT_ARRAY);
        this.add(index, (T) new IntArrayTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addLongArray(long[] value) {
        verifyType(TagType.LONG_ARRAY);
        return this.add((T) new LongArrayTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addLongArray(int index, long[] value) {
        verifyType(TagType.LONG_ARRAY);
        this.add(index, (T) new LongArrayTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addString(String value) {
        verifyType(TagType.STRING);
        return this.add((T) new StringTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addString(int index, String value) {
        verifyType(TagType.STRING);
        this.add(index, (T) new StringTag(value));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @param type The type of the items to be contained in the list.
     * @return True, always.
     */
    public boolean addList(List<? extends Tag> value, TagType type) {
        verifyType(TagType.LIST);
        return this.add((T) new ListTag(value, type));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     * @param type The type of the items to be contained in the list.
     */
    public void addList(int index, List<? extends Tag> value, TagType type) {
        verifyType(TagType.LIST);
        this.add(index, (T) new ListTag(value, type));
    }

    /**
     * Appends the specified value to the end of this list tag.
     *
     * @param value The value to append.
     * @return True, always.
     */
    public boolean addCompound(Map<String, Tag> value) {
        verifyType(TagType.COMPOUND);
        return this.add((T) new CompoundTag(value));
    }

    /**
     * Inserts the specified value at the specified position in this list tag.
     *
     * @param index Index at which the specified tag is to be inserted.
     * @param value The value to be inserted.
     */
    public void addCompound(int index, Map<String, Tag> value) {
        verifyType(TagType.COMPOUND);
        this.add(index, (T) new CompoundTag(value));
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

    private void verifyType(TagType potential) {
        if (potential != this.type) {
            throw new IllegalStateException("Cannot add " + potential.getName() + " to a " + this.type.getName() + " list tag.");
        }
    }
}
