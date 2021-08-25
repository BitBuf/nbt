package dev.dewy.nbt;

import dev.dewy.nbt.tags.collection.CompoundTag;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.*;

@AllArgsConstructor
public class NbtReader {
    private @NonNull TagTypeRegistry typeRegistry;

    public CompoundTag fromStream(@NonNull DataInput input) throws IOException {
        if (input.readByte() != TagType.COMPOUND.getId()) {
            throw new IOException("Root tag in NBT structure must be a compound tag.");
        }

        CompoundTag result = new CompoundTag();

        result.setName(input.readUTF());
        result.read(input, 0, this.typeRegistry);

        return result;
    }

    public TagTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    public void setTypeRegistry(TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }
}
