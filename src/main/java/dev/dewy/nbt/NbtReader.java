package dev.dewy.nbt;

import dev.dewy.nbt.tags.CompoundTag;
import dev.dewy.nbt.tags.RootTag;
import dev.dewy.nbt.utils.CompressionType;
import dev.dewy.nbt.utils.TagType;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public final class NbtReader {
    /**
     * Reads a named root tag (full NBT structure) from a {@link DataInput} stream.
     *
     * @param input The {@link DataInput} stream to read from.
     * @throws IOException If any kind of IO error occurs.
     * @return The root tag.
     */
    public static RootTag fromStream(DataInput input) throws IOException {
        if (input.readByte() != TagType.COMPOUND.getId()) {
            throw new IOException("Root tag must be a compound tag.");
        }

        return new RootTag(input.readUTF(), CompoundTag.read.read(input));
    }

    /**
     * Reads a named root tag (full NBT structure) from a {@link File} with a given kind of compression.
     *
     * @param file The file to read from.
     * @throws IOException If any kind of IO error occurs.
     * @return The root tag.
     */
    public static RootTag fromFile(File file) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        DataInputStream in;

        switch (CompressionType.getCompression(new FileInputStream(file))) {
            case NONE:
                in = new DataInputStream(bis);
                break;
            case GZIP:
                in = new DataInputStream(new GZIPInputStream(bis));
                break;
            case ZLIB:
                in = new DataInputStream(new InflaterInputStream(bis));
                break;
            default:
                throw new IllegalStateException("Illegal compression type. This should never happen.");
        }

        RootTag result = fromStream(in);

        in.close();
        return result;
    }

    /**
     * Reads a named root tag (full NBT structure) from a byte array.
     *
     * @param bytes The byte array to read from.
     * @return The root tag.
     * @throws IOException If any kind of IO error occurs.
     */
    public static RootTag fromByteArray(byte[] bytes) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes)));

        return fromStream(in);
    }

    /**
     * Reads a named root tag (full NBT structure) from a Base64 string.
     *
     * @param base64 The Base64 to read from.
     * @return The root tag.
     * @throws IOException If any kind of IO error occurs.
     */
    public static RootTag fromBase64(String base64) throws IOException {
        return fromByteArray(Base64.getDecoder().decode(base64));
    }
}
