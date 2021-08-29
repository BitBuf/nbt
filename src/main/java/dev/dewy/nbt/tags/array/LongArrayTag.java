package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.primitive.LongTag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The long array tag (type ID 12) is used for storing {@code long[]} arrays in NBT structures.
 * It is not stored as a list of {@link LongTag}s.
 *
 * @author dewy
 */
@NoArgsConstructor
@AllArgsConstructor
public class LongArrayTag extends ArrayTag<Long> {
    private @NonNull long[] value;

    /**
     * Constructs a long array tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code long[]} value.
     */
    public LongArrayTag(String name, @NonNull long[] value) {
        this.setName(name);
        this.setValue(value);
    }

    /**
     * Constructs an unnamed long array tag using a {@code List<>} object.
     *
     * @param value the tag's {@code List<>} value, to be converted to a primitive {@code long[]} array.
     */
    public LongArrayTag(@NonNull List<Long> value) {
        this(null, value);
    }

    /**
     * Constructs a long array tag with a given name, using a List<> object to determine its {@code long[]} value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code List<>} value, to be converted to a primitive {@code long[]} array.
     */
    public LongArrayTag(String name, @NonNull List<Long> value) {
        this.setName(name);
        this.setValue(ArrayUtils.toPrimitive(value.toArray(new Long[0])));
    }

    @Override
    public byte getTypeId() {
        return TagType.LONG_ARRAY.getId();
    }

    @Override
    public long[] getValue() {
        return this.value;
    }

    /**
     * Sets the {@code long[]} value of this long array tag.
     *
     * @param value new {@code long[]} value to be set.
     */
    public void setValue(@NonNull long[] value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeInt(this.value.length);

        for (long l : this.value) {
            output.writeLong(l);
        }
    }

    @Override
    public LongArrayTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = new long[input.readInt()];

        for (int i = 0; i < this.value.length; i++) {
            this.value[i] = input.readLong();
        }

        return this;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public Long get(int index) {
        return this.value[index];
    }

    @Override
    public Long set(int index, @NonNull Long element) {
        return this.value[index] = element;
    }

    @Override
    public void insert(int index, @NonNull Long... elements) {
        this.value = ArrayUtils.insert(index, this.value, ArrayUtils.toPrimitive(elements));
    }

    @Override
    public Long remove(int index) {
        Long previous = this.value[index];
        this.value = ArrayUtils.remove(this.value, index);

        return previous;
    }

    @Override
    public void clear() {
        this.value = new long[0];
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
