package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagRegistry;
import dev.dewy.nbt.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class FloatTag extends NumericalTag<Float> {
    private float value;

    public FloatTag(String name, float value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.FLOAT.getId();
    }

    @Override
    public Float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeFloat(this.value);
    }

    @Override
    public FloatTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = input.readFloat();

        return this;
    }
}
