package dev.dewy.nbt.test;

import dev.dewy.nbt.NbtReader;
import dev.dewy.nbt.tags.CompoundTag;
import dev.dewy.nbt.tags.RootTag;
import dev.dewy.nbt.utils.CompressionType;

import java.io.File;
import java.io.IOException;

public final class NbtTest {
    public static void main(String[] args) throws IOException {
        // Primitive tags in the root tag.
        RootTag root = new RootTag("sample");

        root.getCompound().putString("username", "dewy__");
        root.getCompound().putString("uuid", "01f2376e3ce648fe8b33b58e5db77ea8");
        root.getCompound().putIntArray("ints", new int[] {3, -51, 2});

        // Nested compound tags.
        CompoundTag nested = new CompoundTag();
        root.getCompound().put("nested", nested);

        nested.putDouble("pi", Math.PI);
        nested.putByte("byteMax", Byte.MAX_VALUE);

        System.out.println(root.toBase64());

        // Sample NBT Base64 data.
        System.out.println(NbtReader.fromBase64("CgAGc2FtcGxlCwAEaW50cwAAAAMAAAAD////zQAAAAIIAAR1dWlkACAwMWYyMzc2ZTNjZTY0OGZlOGIzM2I1OGU1ZGI3N2VhOAgACHVzZXJuYW1lAAZkZXd5X18A").getName());

        // Working with NBT files.
        root.toFile(new File("sample.nbt"), CompressionType.NONE);
        root.toFile(new File("samplegzip.nbt"), CompressionType.GZIP);
        root.toFile(new File("samplezlib.nbt"), CompressionType.ZLIB);

        RootTag in = NbtReader.fromFile(new File("samplegzip.nbt")); // Compression determined automatically.
        System.out.println(in.toBase64());
    }
}
