package dev.dewy.nbt.tags.number;

import dev.dewy.nbt.Tag;

/**
 * Interface for an NBT number tag implementation.
 *
 * @author dewy
 */
public interface NumberTag extends Tag {
    /**
     * Returns the value inside the tag as a {@code byte}.
     *
     * @return The value inside the tag as a {@code byte}.
     */
    byte getByte();

    /**
     * Returns the value inside the tag as a {@code short}.
     *
     * @return The value inside the tag as a {@code short}.
     */
    short getShort();

    /**
     * Returns the value inside the tag as a {@code int}.
     *
     * @return The value inside the tag as a {@code int}.
     */
    int getInt();

    /**
     * Returns the value inside the tag as a {@code long}.
     *
     * @return The value inside the tag as a {@code long}.
     */
    long getLong();

    /**
     * Returns the value inside the tag as a {@code float}.
     *
     * @return The value inside the tag as a {@code float}.
     */
    float getFloat();

    /**
     * Returns the value inside the tag as a {@code double}.
     *
     * @return The value inside the tag as a {@code double}.
     */
    double getDouble();
}
