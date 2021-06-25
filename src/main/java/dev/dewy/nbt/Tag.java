package dev.dewy.nbt;

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
}
