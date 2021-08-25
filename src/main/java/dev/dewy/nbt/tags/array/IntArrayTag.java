package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.TagTypeRegistry;
import dev.dewy.nbt.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
public class IntArrayTag extends ArrayTag<Integer> {
    private @NonNull int[] value;

    public IntArrayTag(String name, @NonNull int[] value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.INT_ARRAY.getId();
    }

    @Override
    public int[] getValue() {
        return this.value;
    }

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
    public Integer set(int index, @NonNull Integer newValue) {
        return this.value[index] = newValue;
    }

    @Override
    public void insert(int index, @NonNull Integer... values) {
        this.value = ArrayUtils.insert(index, this.value, ArrayUtils.toPrimitive(values));
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
