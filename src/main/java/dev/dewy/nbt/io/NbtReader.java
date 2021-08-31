package dev.dewy.nbt.io;

import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.collection.CompoundTag;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.IOException;

/**
 * Used to read root {@link CompoundTag}s using a certain {@link TagTypeRegistry}.
 *
 * @author dewy
 */
@AllArgsConstructor
public class NbtReader {
    private @NonNull TagTypeRegistry typeRegistry;

    /**
     * Reads a root {@link CompoundTag} from a {@link DataInput} stream.
     *
     * @param input the stream to read from.
     * @return the root {@link CompoundTag} read from the stream.
     * @throws IOException if any I/O error occurs.
     */
    public CompoundTag fromStream(@NonNull DataInput input) throws IOException {
        if (input.readByte() != TagType.COMPOUND.getId()) {
            throw new IOException("Root tag in NBT structure must be a compound tag.");
        }

        CompoundTag result = new CompoundTag();

        result.setName(input.readUTF());
        result.read(input, 0, this.typeRegistry);

        return result;
    }

    /**
     * Returns the {@link TagTypeRegistry} currently in use by this reader.
     *
     * @return the {@link TagTypeRegistry} currently in use by this reader.
     */
    public TagTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    /**
     * Sets the {@link TagTypeRegistry} currently in use by this reader. Used to utilise custom-made tag types.
     *
     * @param typeRegistry the new {@link TagTypeRegistry} to be set.
     */
    public void setTypeRegistry(@NonNull TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }
}
