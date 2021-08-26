package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagType;
import dev.dewy.nbt.TagTypeRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class IntTag extends NumericalTag<Integer> {
    private int value;

    public IntTag(String name, int value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.INT.getId();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeInt(this.value);
    }

    @Override
    public IntTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = input.readInt();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntTag intTag = (IntTag) o;

        return value == intTag.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
