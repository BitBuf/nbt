package dev.dewy.nbt;

import dev.dewy.nbt.exceptions.TagTypeRegistryException;

import dev.dewy.nbt.tags.array.*;
import dev.dewy.nbt.tags.collection.*;
import dev.dewy.nbt.tags.primitive.*;

public enum TagType {
    BYTE(1),
    SHORT(2),
    INT(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);

    private final int id;

    TagType(int id) {
        this.id = id;
    }

    public byte getId() {
        return (byte) id;
    }

    static void registerAll(TagTypeRegistry registry) {
        try {
            registry.registerTagType(BYTE.getId(), ByteTag.class);
            registry.registerTagType(SHORT.getId(), ShortTag.class);
            registry.registerTagType(INT.getId(), IntTag.class);
            registry.registerTagType(LONG.getId(), LongTag.class);
            registry.registerTagType(FLOAT.getId(), FloatTag.class);
            registry.registerTagType(DOUBLE.getId(), DoubleTag.class);
            registry.registerTagType(BYTE_ARRAY.getId(), ByteArrayTag.class);
            registry.registerTagType(STRING.getId(), StringTag.class);
            registry.registerTagType(LIST.getId(), ListTag.class);
            registry.registerTagType(COMPOUND.getId(), CompoundTag.class);
            registry.registerTagType(INT_ARRAY.getId(), IntArrayTag.class);
            registry.registerTagType(LONG_ARRAY.getId(), LongArrayTag.class);
        } catch (TagTypeRegistryException e) {
            // Should never happen.
            e.printStackTrace();
        }
    }
}
