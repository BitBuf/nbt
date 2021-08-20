package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.Tag;

public abstract class ArrayTag<T> extends Tag {
    public abstract int size();

    public abstract T get(int index);

    public abstract T set(int index, T newValue);

    public abstract void insert(int index, T... values);

    public abstract T remove(int index);

    public abstract void clear();
}
