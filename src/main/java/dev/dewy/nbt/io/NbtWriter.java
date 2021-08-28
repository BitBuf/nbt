package dev.dewy.nbt.io;

import dev.dewy.nbt.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.collection.CompoundTag;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.DataOutput;
import java.io.IOException;

/**
 * Used to write root {@link CompoundTag}s using a certain {@link TagTypeRegistry}.
 *
 * @author dewy
 */
@AllArgsConstructor
public class NbtWriter {
    private @NonNull TagTypeRegistry typeRegistry;

    /**
     * Writes the given root {@link CompoundTag} to a {@link DataOutput} stream.
     *
     * @param compound the NBT structure to write, contained within a {@link CompoundTag}.
     * @param output the stream to write to.
     * @throws IOException if any I/O error occurs.
     */
    public void toStream(@NonNull CompoundTag compound, @NonNull DataOutput output) throws IOException {
        output.writeByte(TagType.COMPOUND.getId());

        if (compound.getName() == null) {
            output.writeUTF("");
        } else {
            output.writeUTF(compound.getName());
        }

        compound.write(output, 0, this.typeRegistry);
    }

    /**
     * Returns the {@link TagTypeRegistry} currently in use by this writer.
     *
     * @return the {@link TagTypeRegistry} currently in use by this writer.
     */
    public TagTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    /**
     * Sets the {@link TagTypeRegistry} currently in use by this writer. Used to utilise custom-made tag types.
     *
     * @param typeRegistry the new {@link TagTypeRegistry} to be set.
     */
    public void setTypeRegistry(TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }
}
