package dev.dewy.nbt.tags.array;

import dev.dewy.nbt.Tag;

/**
 * Interface for an NBT array tag implementation.
 *
 * @author dewy
 */
public interface ArrayTag extends Tag {
    /**
     * Returns the size of the array inside the tag.
     *
     * @return The size of the array inside the tag.
     */
    int size();
}
