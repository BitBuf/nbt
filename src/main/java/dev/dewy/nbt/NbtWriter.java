package dev.dewy.nbt;

import dev.dewy.nbt.tags.collection.CompoundTag;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.*;

@AllArgsConstructor
public class NbtWriter {
    private @NonNull TagTypeRegistry typeRegistry;

    public void toStream(@NonNull CompoundTag compound, @NonNull DataOutput output) throws IOException {
        output.writeByte(TagType.COMPOUND.getId());

        if (compound.getName() == null) {
            output.writeUTF("");
        } else {
            output.writeUTF(compound.getName());
        }

        compound.write(output, 0, this.typeRegistry);
    }

    public TagTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    public void setTypeRegistry(TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }
}
