package dev.dewy.nbt.test;

import dev.dewy.nbt.tags.CompoundTag;
import dev.dewy.nbt.tags.ListTag;
import dev.dewy.nbt.tags.StringTag;
import dev.dewy.nbt.tags.array.ByteArrayTag;
import dev.dewy.nbt.tags.number.ShortTag;
import dev.dewy.nbt.utils.CompressionType;

import java.io.*;
import java.util.Arrays;

public class NBTTest {
    public static void main(String[] args) {
        write();
        read();
    }

    private static void write() {
        // Initializing tag contents.

        short number = 3;
        byte[] array = new byte[]{3, -52, 123, -6};

        ListTag<StringTag> cheeses = new ListTag<>();

        cheeses.add(new StringTag("cheddar"));
        cheeses.add(new StringTag("king slime"));
        cheeses.add(new StringTag("blue"));
        cheeses.add(new StringTag("american"));

        // Compilation into a single root compound.

        CompoundTag root = new CompoundTag();

        root.putShort("number", number);
        root.putByteArray("array", array);
        root.putString("string", "Here's some weird stuff --> '\"/'/\\'");

        // cheese is a ListTag, not a raw List, so we use put() instead of putList()
        root.put("cheeses", cheeses);

        // Writing root compound to sample.nbt

        try {
            root.writeRootToFile("sample", new File("sample.nbt"), CompressionType.GZIP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void read() {
        CompoundTag root;

        try {
            root = CompoundTag.fromFile(new File("sample.nbt"), CompressionType.GZIP).getTag();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ShortTag number = root.getShort("number");
        ByteArrayTag array = root.getByteArray("array");
        StringTag string = root.getString("string");
        ListTag<StringTag> cheeses = root.getList("cheeses");

        // Printing them out.

        System.out.println(number.getValue());
        System.out.println(Arrays.toString(array.getValue()));
        System.out.println(string.getValue());

        for (StringTag s : cheeses) {
            System.out.println(s.getValue());
        }
    }
}
