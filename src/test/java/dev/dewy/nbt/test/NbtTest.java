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

public class NbtTest {
    public static final Nbt NBT = new Nbt();

    public static void main(String[] args) throws IOException {
        CompoundTag root = new CompoundTag("root");

        root.put(new ByteTag("byte", 45));
        root.put(new ShortTag("short", 345));
        root.put(new IntTag("int", -981735));
        root.put(new LongTag("long", -398423290489L));
        root.put(new FloatTag("float", 12.5F));
        root.put(new DoubleTag("double", -19040912.1235));
        root.put(new StringTag("string", "https://dewy.dev"));

        root.put(new ByteArrayTag("bytes", new byte[] {0, -124, 13, -6, Byte.MAX_VALUE}));
        root.put(new IntArrayTag("ints", new int[] {0, -1348193, 817519, Integer.MIN_VALUE, 4}));
        root.put(new LongArrayTag("longs", new long[] {12490812, 903814091904L, -3, Long.MIN_VALUE, Long.MAX_VALUE, 0}));

        CompoundTag subCompound = new CompoundTag("sub");
        ListTag<CompoundTag> doubles = new ListTag<>("listmoment");

        for (int i = 0; i < 1776; i++) {
            CompoundTag tmp = new CompoundTag("tmp" + i);

            tmp.put(new DoubleTag("i", i));
            tmp.put(new DoubleTag("n", i - 1348.1));

            doubles.add(tmp);
        }

        subCompound.put(doubles);
        root.put(subCompound);

        ListTag<CompoundTag> compounds = new ListTag<>("compounds");
        compounds.add(new CompoundTag());
        root.put(compounds);

        ListTag<ListTag<IntTag>> listsOfInts = new ListTag<>("listofints");
        listsOfInts.add(new ListTag<>());
        root.put(listsOfInts);

        NBT.toFile(root, new File("sample.nbt"), CompressionType.NONE);
        NBT.toFile(root, new File("samplegzip.nbt"), CompressionType.GZIP);
        NBT.toFile(root, new File("samplezlib.nbt"), CompressionType.ZLIB);

        System.out.println(NBT.toBase64(root));

        CompoundTag clone = NBT.fromFile(new File("samplezlib.nbt"));

        System.out.println(clone.getName());
        System.out.println("Be sure to visit " + clone.<StringTag>get("string").getValue() + " c:");
    }
}
