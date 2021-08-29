package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.primitive.IntTag;
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
 * The int array tag (type ID 11) is used for storing {@code int[]} arrays in NBT structures.
 * It is not stored as a list of {@link IntTag}s.
 *
 * @author dewy
 */
@NoArgsConstructor
@AllArgsConstructor
public class IntArrayTag extends ArrayTag<Integer> {
    private @NonNull int[] value;

    /**
     * Constructs an int array tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code int[]} value.
     */
    public IntArrayTag(String name, @NonNull int[] value) {
        this.setName(name);
        this.setValue(value);
    }

    /**
     * Constructs an unnamed int array tag using a {@code List<>} object.
     *
     * @param value the tag's {@code List<>} value, to be converted to a primitive {@code int[]} array.
     */
    public IntArrayTag(@NonNull List<Integer> value) {
        this(null, value);
    }

    /**
     * Constructs an int array tag with a given name, using a List object to determine its {@code int[]} value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code List<>} value, to be converted to a primitive {@code int[]} array.
     */
    public IntArrayTag(String name, @NonNull List<Integer> value) {
        this.setName(name);
        this.setValue(ArrayUtils.toPrimitive(value.toArray(new Integer[0])));
    }

    @Override
    public byte getTypeId() {
        return TagType.INT_ARRAY.getId();
    }

    @Override
    public int[] getValue() {
        return this.value;
    }

    /**
     * Sets the {@code int[]} value of this int array tag.
     *
     * @param value new {@code int[]} value to be set.
     */
    public void setValue(@NonNull int[] value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeInt(this.value.length);

        for (int i : this.value) {
            output.writeInt(i);
        }
    }

    @Override
    public IntArrayTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = new int[input.readInt()];

        for (int i = 0; i < this.value.length; i++) {
            this.value[i] = input.readInt();
        }

        return this;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public Integer get(int index) {
        return this.value[index];
    }

    @Override
    public Integer set(int index, @NonNull Integer element) {
        return this.value[index] = element;
    }

    @Override
    public void insert(int index, @NonNull Integer... elements) {
        this.value = ArrayUtils.insert(index, this.value, ArrayUtils.toPrimitive(elements));
    }

    @Override
    public Integer remove(int index) {
        Integer previous = this.value[index];
        this.value = ArrayUtils.remove(this.value, index);

        return previous;
    }

    @Override
    public void clear() {
        this.value = new int[0];
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
