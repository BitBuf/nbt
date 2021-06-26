package dev.dewy.nbt.utils;

import dev.dewy.nbt.Tag;
import dev.dewy.nbt.TagType;
import dev.dewy.nbt.tags.CompoundTag;
import dev.dewy.nbt.tags.ListTag;
import dev.dewy.nbt.tags.StringTag;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.number.*;

import java.io.DataInput;
import java.io.IOException;

/**
 * Represents a function which accepts a {@link DataInput} stream as an argument and reads an object from it.
 *
 * @param <T> The {@link DataInput} stream to read from.
 * @param <R> The object read from the stream.
 *
 * @author dewy
 */
@FunctionalInterface
public interface ReadFunction<T extends DataInput, R> {
    R read(T t) throws IOException;

    /**
     * Returns the read function for a given {@link TagType}.
     *
     * @param type The tag type.
     * @return The read function of the tag type.
     */
    static ReadFunction<DataInput, ? extends Tag> of(TagType type) {
        switch (type) {
            case BYTE:
                return ByteTag.read;
            case SHORT:
                return ShortTag.read;
            case INT:
                return IntTag.read;
            case LONG:
                return LongTag.read;
            case FLOAT:
                return FloatTag.read;
            case DOUBLE:
                return DoubleTag.read;
            case BYTE_ARRAY:
                return ByteArrayTag.read;
            case STRING:
                return StringTag.read;
            case LIST:
                return ListTag.read;
            case COMPOUND:
                return CompoundTag.read;
            case INT_ARRAY:
                return IntArrayTag.read;
            case LONG_ARRAY:
                return LongArrayTag.read;
            default:
                throw new IllegalArgumentException("Could not get read function of tag type " + type.getName());
        }
    }
}
