package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.api.json.JsonSerializable;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.snbt.SnbtConfig;
import dev.dewy.nbt.api.snbt.SnbtSerializable;

import java.util.regex.Pattern;

/**
 * Abstract class for implementing NBT array tags.
 *
 * @param <T> the type held in the array.
 * @author dewy
 */
public abstract class ArrayTag<T> extends Tag implements SnbtSerializable, JsonSerializable, Iterable<T> {
    public static final Pattern NUMBER_PATTERN = Pattern.compile("[-0-9]+");

    /**
     * Returns the number of elements in this array tag.
     *
     * @return the number of elements in this array tag.
     */
    public abstract int size();

    /**
     * Returns the element at the specified position in this array tag.
     *
     * @param index index of the element to return.
     * @return the element at the specified position in this array tag.
     */
    public abstract T get(int index);

    /**
     * Replaces the element at the specified position in this array tag with the specified element.
     *
     * @param index index of the element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     */
    public abstract T set(int index, T element);

    /**
     * Inserts the specified element(s) at the specified position in this array tag.
     * Shifts the element(s) currently at that position and any subsequent elements to the right.
     *
     * @param index index at which the element(s) are to be inserted.
     * @param elements element(s) to be inserted.
     */
    public abstract void insert(int index, T... elements);

    /**
     * Appends the specified element(s) to the end of the array tag.
     *
     * @param elements element(s) to be added.
     */
    @SafeVarargs
    public final void add(T... elements) {
        this.insert(this.size() - 1, elements);
    }

    /**
     * Removes the element at the specified position in this array tag.
     * Shifts any subsequent elements to the left. Returns the element that was removed from the array tag.
     *
     * @param index the index of the element to be removed.
     * @return the element previously at the specified position.
     */
    public abstract T remove(int index);

    /**
     * Removes all the elements from this array tag. The array tag will be empty after this call returns.
     */
    public abstract void clear();

    @Override
    public String toString() {
        return this.toSnbt(0, new TagTypeRegistry(), new SnbtConfig());
    }
}
