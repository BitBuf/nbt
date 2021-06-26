package dev.dewy.nbt;

import dev.dewy.nbt.utils.ReadFunction;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Interface for an NBT tag implementation.
 *
 * @author dewy
 */
public interface Tag {
    /**
     * Returns the {@link TagType} being implemented.
     *
     * @return The {@link TagType} being implemented.
     */
    TagType getType();

    /**
     * Writes the data inside the NBT tag to a {@link DataOutput} stream.
     *
     * @param output The stream to write to.
     * @throws IOException If any IO error occurs.
     */
    void write(DataOutput output) throws IOException;

    /**
     * Returns the {@link ReadFunction} associated with the NBT tag.
     *
     * @return The {@link ReadFunction} associated with the NBT tag.
     */
    ReadFunction<DataInput, ? extends Tag> getReader();
}
