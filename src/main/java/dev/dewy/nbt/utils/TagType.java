package dev.dewy.nbt.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines the 13 different types of NBT tags there are.
 *
 * @author dewy
 */
public enum TagType {
    /**
     * The end tag is used as a marker for the end of list and compound tags.
     * All valid NBT files end with an end tag, as all data is contained within a root compound tag.
     *
     * Pretend this doesn't exist, unless you know what you're doing.
     */
    END(0, "TAG_End"),

    /**
     * The byte tag is used for storing 8-bit signed two's complement integers;
     * Java's primitive type {@code byte}.
     */
    BYTE(1, "TAG_Byte"),

    /**
     * The short tag is used for storing 16-bit signed two's complement integers;
     * Java's primitive type {@code short}.
     */
    SHORT(2, "TAG_Short"),

    /**
     * The int tag is used for storing 32-bit signed two's complement integers;
     * Java's primitive type {@code int}.
     */
    INT(3, "TAG_Int"),

    /**
     * The long tag is used for storing 64-bit signed two's complement integers;
     * Java's primitive type {@code long}.
     */
    LONG(4, "TAG_Long"),

    /**
     * The float tag is used for storing single-precision 32-bit IEEE 754 floating point values;
     * Java's primitive type {@code float}.
     */
    FLOAT(5, "TAG_Float"),

    /**
     * The double tag is used for storing double-precision 64-bit IEEE 754 floating point values;
     * Java's primitive type {@code double}.
     */
    DOUBLE(6, "TAG_Double"),

    /**
     * The byte array tag is used for storing arrays of Java primitive {@code byte}s.
     * It stores a raw byte array, NOT a list of byte tags.
     */
    BYTE_ARRAY(7, "TAG_Byte_Array"),

    /**
     * The string tag is used for storing UTF-8 encoded strings, prefixed by a length stored as a 32-bit int.
     */
    STRING(8, "TAG_String"),

    /**
     * The list tag is used for storing a list of unnamed NBT tags all of the same type.
     */
    LIST(9, "TAG_List"),

    /**
     * The compound tag is used for storing a list of named NBT tags that don't have to be of the same type, unlike the list tag.
     * Every valid NBT file begins with a compound tag, with all data being stored within it: the root compound.
     */
    COMPOUND(10, "TAG_Compound"),

    /**
     * The int array tag is used for storing arrays of Java primitive {@code int}s.
     * It stores a raw int array, NOT a list of int tags.
     */
    INT_ARRAY(11, "TAG_Int_Array"),

    /**
     * The long array tag is used for storing arrays of Java primitive {@code long}s.
     * It stores a raw long array, NOT a list of long tags.
     */
    LONG_ARRAY(12, "TAG_Long_Array");

    private final int id;
    private final String name;

    TagType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the ID corresponding to the tag type.
     *
     * @return The ID corresponding to the tag type.
     */
    public byte getId() {
        return (byte) id;
    }

    /**
     * Returns the verbose name of the tag type.
     *
     * @return The verbose name of the tag type.
     */
    public String getName() {
        return name;
    }

    private static final Map<Byte, TagType> ID_LOOKUP = new HashMap<>();

    /**
     * Returns a type ID's corresponding {@link TagType}.
     *
     * @param in The type ID.
     * @return The corresponding {@link TagType}.
     */
    public static TagType fromByte(byte in) {
        if (ID_LOOKUP.isEmpty()) {
            for (TagType type : TagType.values()) {
                ID_LOOKUP.put(type.getId(), type);
            }
        }

        return ID_LOOKUP.get(in);
    }
}