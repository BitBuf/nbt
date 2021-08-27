package dev.dewy.nbt;

import dev.dewy.nbt.tags.collection.CompoundTag;
import lombok.NonNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * Standard interface for reading and writing NBT data structures.
 *
 * @author dewy
 */
public class Nbt {
    private @NonNull TagTypeRegistry typeRegistry;
    private final @NonNull NbtWriter writer;
    private final @NonNull NbtReader reader;

    /**
     * Constructs an instance of this class using a default {@link TagTypeRegistry} (supporting the standard 12 tag types).
     */
    public Nbt() {
        this(new TagTypeRegistry());
    }

    /**
     * Constructs an instance of this class using a given {@link TagTypeRegistry}.
     *
     * @param typeRegistry the tag type registry to be used, typically containing custom tag entries.
     */
    public Nbt(@NonNull TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;

        this.writer = new NbtWriter(typeRegistry);
        this.reader = new NbtReader(typeRegistry);
    }

    /**
     * Writes the given root {@link CompoundTag} to a provided {@link DataOutput} stream.
     *
     * @param compound the NBT structure to write, contained within a {@link CompoundTag}.
     * @param output the stream to write to.
     * @throws IOException if any I/O error occurs.
     */
    public void toStream(@NonNull CompoundTag compound, @NonNull DataOutput output) throws IOException {
        this.writer.toStream(compound, output);
    }

    /**
     * Writes the given root {@link CompoundTag} to a {@link File} with no compression.
     *
     * @param compound the NBT structure to write, contained within a {@link CompoundTag}.
     * @param file the file to write to.
     * @throws IOException if any I/O error occurs.
     */
    public void toFile(@NonNull CompoundTag compound, @NonNull File file) throws IOException {
        this.toFile(compound, file, CompressionType.NONE);
    }

    /**
     * Writes the given root {@link CompoundTag} to a {@link File} using a certain {@link CompressionType}.
     *
     * @param compound the NBT structure to write, contained within a {@link CompoundTag}.
     * @param file the file to write to.
     * @param compression the compression to be applied.
     * @throws IOException if any I/O error occurs.
     */
    public void toFile(@NonNull CompoundTag compound, @NonNull File file, @NonNull CompressionType compression) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        DataOutputStream dos = null;

        switch (compression) {
            case NONE:
                dos = new DataOutputStream(bos);
                break;
            case GZIP:
                dos = new DataOutputStream(new GZIPOutputStream(bos));
                break;
            case ZLIB:
                dos = new DataOutputStream(new DeflaterOutputStream(bos));
        }

        this.toStream(compound, dos);
        dos.close();
    }

    /**
     * Converts the given root {@link CompoundTag} to a {@code byte[]} array.
     *
     * @param compound the NBT structure to write, contained within a {@link CompoundTag}.
     * @return the resulting {@code byte[]} array.
     * @throws IOException if any I/O error occurs.
     */
    public byte[] toByteArray(@NonNull CompoundTag compound) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream w = new DataOutputStream(new BufferedOutputStream(baos));

        this.toStream(compound, w);
        w.flush();

        return baos.toByteArray();
    }

    /**
     * Converts the given root {@link CompoundTag} to a Base64 encoded string.
     *
     * @param compound the NBT structure to write, contained within a {@link CompoundTag}.
     * @return the resulting Base64 encoded string.
     * @throws IOException if any I/O error occurs.
     */
    public String toBase64(@NonNull CompoundTag compound) throws IOException {
        return new String(Base64.getEncoder().encode(this.toByteArray(compound)), StandardCharsets.UTF_8);
    }

    /**
     * Reads an NBT data structure (root {@link CompoundTag}) from a {@link DataInput} stream.
     *
     * @param input the stream to read from.
     * @return the root {@link CompoundTag} read from the stream.
     * @throws IOException if any I/O error occurs.
     */
    public CompoundTag fromStream(@NonNull DataInput input) throws IOException {
        return this.reader.fromStream(input);
    }

    /**
     * Reads an NBT data structure (root {@link CompoundTag}) from a {@link File}.
     *
     * @param file the file to read from.
     * @return the root {@link CompoundTag} read from the stream.
     * @throws IOException if any I/O error occurs.
     */
    public CompoundTag fromFile(@NonNull File file) throws IOException {
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

        CompoundTag result = this.fromStream(in);
        in.close();

        return result;
    }

    /**
     * Reads an NBT data structure (root {@link CompoundTag}) from a {@code byte[]} array.
     *
     * @param bytes the {@code byte[]} array to read from.
     * @return the root {@link CompoundTag} read from the stream.
     * @throws IOException if any I/O error occurs.
     */
    public CompoundTag fromByteArray(@NonNull byte[] bytes) throws IOException {
        return fromStream(new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes))));
    }

    /**
     * Decodes an NBT data structure (root {@link CompoundTag}) from a Base64 encoded string.
     *
     * @param encoded the encoded Base64 string to decode.
     * @return the decoded root {@link CompoundTag}.
     * @throws IOException if any I/O error occurs.
     */
    public CompoundTag fromBase64(@NonNull String encoded) throws IOException {
        return fromByteArray(Base64.getDecoder().decode(encoded));
    }

    /**
     * Returns the {@link TagTypeRegistry} currently in use by this instance.
     *
     * @return the {@link TagTypeRegistry} currently in use by this instance.
     */
    public TagTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    /**
     * Sets the {@link TagTypeRegistry} currently in use by this instance. Used to utilise custom-made tag types.
     *
     * @param typeRegistry the new {@link TagTypeRegistry} to be set.
     */
    public void setTypeRegistry(@NonNull TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;

        this.writer.setTypeRegistry(typeRegistry);
        this.reader.setTypeRegistry(typeRegistry);
    }
}
