package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.tags.Tag;

public abstract class NumericalTag<T extends Number> extends Tag {
    @Override
    public abstract T getValue();

    public byte byteValue() {
        return this.getValue().byteValue();
    }

    public short shortValue() {
        return this.getValue().shortValue();
    }

    public int intValue() {
        return this.getValue().intValue();
    }

    public long longValue() {
        return this.getValue().longValue();
    }

    public float floatValue() {
        return this.getValue().floatValue();
    }

    public double doubleValue() {
        return this.getValue().doubleValue();
    }
}
