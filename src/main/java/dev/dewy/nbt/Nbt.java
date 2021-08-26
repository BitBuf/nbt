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

public class Nbt {
    private @NonNull TagTypeRegistry typeRegistry;
    private @NonNull NbtWriter writer;
    private @NonNull NbtReader reader;

    public Nbt() {
        this(new TagTypeRegistry());
    }

    public Nbt(@NonNull TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;

        this.writer = new NbtWriter(typeRegistry);
        this.reader = new NbtReader(typeRegistry);
    }

    public void toStream(@NonNull CompoundTag compound, @NonNull DataOutput output) throws IOException {
        this.writer.toStream(compound, output);
    }

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

    public byte[] toByteArray(@NonNull CompoundTag compound) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream w = new DataOutputStream(new BufferedOutputStream(baos));

        this.toStream(compound, w);
        w.flush();

        return baos.toByteArray();
    }

    public String toBase64(@NonNull CompoundTag compound) throws IOException {
        return new String(Base64.getEncoder().encode(this.toByteArray(compound)), StandardCharsets.UTF_8);
    }

    public CompoundTag fromStream(@NonNull DataInput input) throws IOException {
        return this.reader.fromStream(input);
    }

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

    public CompoundTag fromByteArray(@NonNull byte[] bytes) throws IOException {
        return fromStream(new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(bytes))));
    }

    public CompoundTag fromBase64(@NonNull String encoded) throws IOException {
        return fromByteArray(Base64.getDecoder().decode(encoded));
    }

    public TagTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    public void setTypeRegistry(@NonNull TagTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;

        this.writer.setTypeRegistry(typeRegistry);
        this.reader.setTypeRegistry(typeRegistry);
    }
}
