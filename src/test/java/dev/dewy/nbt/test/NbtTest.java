package dev.dewy.nbt.test;

import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.io.CompressionType;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.array.IntArrayTag;
import dev.dewy.nbt.tags.array.LongArrayTag;
import dev.dewy.nbt.tags.collection.CompoundTag;
import dev.dewy.nbt.tags.collection.ListTag;
import dev.dewy.nbt.tags.primitive.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple demonstration of how the NBT library may be used.
 *
 * @author dewy
 */
public class NbtTest {
    // paths for the sample files
    private static final String SAMPLES_PATH = "samples/";
    private static final File STANDARD_SAMPLE = new File(SAMPLES_PATH + "sample.nbt");
    private static final File GZIP_SAMPLE = new File(SAMPLES_PATH + "samplegzip.nbt");
    private static final File ZLIB_SAMPLE = new File(SAMPLES_PATH + "samplezlib.nbt");
    private static final File JSON_SAMPLE = new File(SAMPLES_PATH + "sample.json");

    // instance of the Nbt class, globally used. use setTypeRegistry() to use custom-made tag types.
    private static final Nbt NBT = new Nbt();

    public static void main(String[] args) throws IOException {
        // creation of a root compound (think of it like a JSONObject in GSON)
        CompoundTag root = new CompoundTag("root");

        // primitive NBT tags (tags contained inside compounds MUST have unique names)
        root.put(new ByteTag("byte", 45));
        root.put(new ShortTag("short", 345));
        root.put(new IntTag("int", -981735));
        root.put(new LongTag("long", -398423290489L));

        // more primitives, using the specialized put methods
        root.putFloat("float", 12.5F);
        root.putDouble("double", -19040912.1235);

        // putting a previously unnamed tag.
        root.put("string", new StringTag("https://dewy.dev"));

        // array NBT tags
        root.put(new ByteArrayTag("bytes", new byte[] {0, -124, 13, -6, Byte.MAX_VALUE}));
        root.put(new IntArrayTag("ints", new int[] {0, -1348193, 817519, Integer.MIN_VALUE, 4}));

        // constructing array tags with List<> objects
        List<Long> longList = new ArrayList<>();
        longList.add(12490812L);
        longList.add(903814091904L);
        longList.add(-3L);
        longList.add(Long.MIN_VALUE);
        longList.add(Long.MAX_VALUE);
        longList.add(0L);

        root.put(new LongArrayTag("longs", longList));

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
        root.putList("listofints", listsOfInts.getValue());

        // writing to file (no compression type provided for no compression)
        NBT.toFile(root, STANDARD_SAMPLE);
        NBT.toFile(root, GZIP_SAMPLE, CompressionType.GZIP);
        NBT.toFile(root, ZLIB_SAMPLE, CompressionType.ZLIB);

        // displaying a Base64 representation
        System.out.println(NBT.toBase64(root));

        // reading from file
        CompoundTag clone = NBT.fromFile(ZLIB_SAMPLE);
        System.out.println(clone.equals(root));

        // retrieving data from the read compound
        System.out.println(clone.getName());
        System.out.println("Be sure to visit " + clone.getString("string").getValue() + " c:");

        // nbt to json and back: see readme for NBT JSON format documentation
        jsonTest();

        // displaying as SNBT
        System.out.println(root);
    }

    private static void jsonTest() throws IOException {
        CompoundTag root = new CompoundTag("root");

        root.putInt("primitive", 3);
        root.putIntArray("array", new int[]{0, 1, 2, 3});

        List<StringTag> list = new LinkedList<>();
        list.add(new StringTag("duck"));
        list.add(new StringTag("goose"));

        root.putList("list", list);
        root.put("compound", new CompoundTag());

        NBT.toJson(root, JSON_SAMPLE);
        System.out.println(NBT.fromJson(JSON_SAMPLE).equals(root));
    }
}
