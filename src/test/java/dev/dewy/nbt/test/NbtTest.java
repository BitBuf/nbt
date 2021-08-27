package dev.dewy.nbt.test;

import dev.dewy.nbt.CompressionType;
import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.collection.CompoundTag;
import dev.dewy.nbt.tags.collection.ListTag;
import dev.dewy.nbt.tags.primitive.*;

import java.io.File;
import java.io.IOException;

/**
 * A simple demonstration of how the NBT library may be used.
 *
 * @author dewy
 */
public class NbtTest {
    // instance of the Nbt class, globally used. use setTypeRegistry() to use custom-made tag types.
    public static final Nbt NBT = new Nbt();

    public static void main(String[] args) throws IOException {
        // creation of a root compound (think of it like a JSONObject in GSON)
        CompoundTag root = new CompoundTag("root");

        // primitive NBT tags (tags contained inside compounds MUST have unique names)
        root.put(new ByteTag("byte", 45));
        root.put(new ShortTag("short", 345));
        root.put(new IntTag("int", -981735));
        root.put(new LongTag("long", -398423290489L));
        root.put(new FloatTag("float", 12.5F));
        root.put(new DoubleTag("double", -19040912.1235));
        root.put(new StringTag("string", "https://dewy.dev"));

        // array NBT tags
        root.put(new ByteArrayTag("bytes", new byte[] {0, -124, 13, -6, Byte.MAX_VALUE}));
        root.put(new IntArrayTag("ints", new int[] {0, -1348193, 817519, Integer.MIN_VALUE, 4}));
        root.put(new LongArrayTag("longs", new long[] {12490812, 903814091904L, -3, Long.MIN_VALUE, Long.MAX_VALUE, 0}));

        // compound and list tags
        CompoundTag subCompound = new CompoundTag("sub");
        ListTag<CompoundTag> doubles = new ListTag<>("listmoment");

        for (int i = 0; i < 1776; i++) {
            CompoundTag tmp = new CompoundTag("tmp" + i);

            tmp.put(new DoubleTag("i", i));
            tmp.put(new DoubleTag("n", i / 1348.1));

            doubles.add(tmp);
        }

        subCompound.put(doubles);
        root.put(subCompound);

        // compound containing an empty compound
        ListTag<CompoundTag> compounds = new ListTag<>("compounds");
        compounds.add(new CompoundTag());
        root.put(compounds);

        // list containing an empty list of ints
        ListTag<ListTag<IntTag>> listsOfInts = new ListTag<>("listofints");
        listsOfInts.add(new ListTag<>());
        root.put(listsOfInts);

        // writing to file (no compression type provided for no compression)
        NBT.toFile(root, new File("sample.nbt"));
        NBT.toFile(root, new File("samplegzip.nbt"), CompressionType.GZIP);
        NBT.toFile(root, new File("samplezlib.nbt"), CompressionType.ZLIB);

        // displaying a Base64 representation
        System.out.println(NBT.toBase64(root));

        // reading from file
        CompoundTag clone = NBT.fromFile(new File("samplezlib.nbt"));
        System.out.println(clone.equals(root));

        // retrieving data from the read compound
        System.out.println(clone.getName());
        System.out.println("Be sure to visit " + clone.<StringTag>get("string").getValue() + " c:");
    }
}
