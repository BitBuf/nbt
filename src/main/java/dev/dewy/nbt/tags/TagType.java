package dev.dewy.nbt.tags;

import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.registry.TagTypeRegistryException;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.collection.CompoundTag;
import dev.dewy.nbt.tags.collection.ListTag;
import dev.dewy.nbt.tags.primitive.*;

/**
 * Defines the 12 standard NBT tag types and their IDs supported by this library, laid out in the Notchian spec.
 *
 * @author dewy
 */
public enum TagType {
    /**
     * ID: 1
     *
     * @see ByteTag
     */
    BYTE(1),

    /**
     * ID: 2
     *
     * @see ShortTag
     */
    SHORT(2),

    /**
     * ID: 3
     *
     * @see IntTag
     */
    INT(3),

    /**
     * ID: 4
     *
     * @see LongTag
     */
    LONG(4),

    /**
     * ID: 5
     *
     * @see FloatTag
     */
    FLOAT(5),

    /**
     * ID: 6
     *
     * @see DoubleTag
     */
    DOUBLE(6),

    /**
     * ID: 7
     *
     * @see ByteArrayTag
     */
    BYTE_ARRAY(7),

    /**
     * ID: 8
     *
     * @see StringTag
     */
    STRING(8),

    /**
     * ID: 9
     *
     * @see ListTag
     */
    LIST(9),

    /**
     * ID: 10
     *
     * @see CompoundTag
     */
    COMPOUND(10),

    /**
     * ID: 11
     *
     * @see IntArrayTag
     */
    INT_ARRAY(11),

    /**
     * ID: 12
     *
     * @see LongArrayTag
     */
    LONG_ARRAY(12);

    private final int id;

    TagType(int id) {
        this.id = id;
    }

    public byte getId() {
        return (byte) id;
    }

    public static void registerAll(TagTypeRegistry registry) {
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
