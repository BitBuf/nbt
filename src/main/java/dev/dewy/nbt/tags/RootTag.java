package dev.dewy.nbt.tags;

import dev.dewy.nbt.utils.CompressionType;
import dev.dewy.nbt.utils.TagType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

/**
 * Represents the root compound tag.
 *
 * @author dewy
 */
public class RootTag {
    private String name;
    private CompoundTag compound;

    public RootTag() {
        this("");
    }

    public RootTag(String name) {
        this(name, new CompoundTag());
    }

    public RootTag(String name, CompoundTag compound) {
        if (name == null) {
            this.name = "";
        } else {
            this.name = name;
        }

        this.compound = compound;
    }

    public String getName() {
        return name;
    }

    public CompoundTag getCompound() {
        return compound;
    }

    public void setName(String name) {
        if (name == null) {
            this.name = "";
        }

        this.name = name;
    }

    public void setCompound(CompoundTag compound) {
        this.compound = compound;
    }

    /**
     * Write the root tag to a {@link DataOutput} stream.
     *
     * @param output The stream to write to.
     * @throws IOException If any IO error occurs.
     */
    public void toStream(DataOutput output) throws IOException {
        output.writeByte(TagType.COMPOUND.getId());
        output.writeUTF(this.name);

        this.compound.write(output);
    }

    /**
     * Write the compound tag to a {@link File} with a name of its own, using a given compression scheme.
     *
     * @param file The file to be written to.
     * @param compression The compression to be applied.
     * @throws IOException If any IO error occurs.
     */
    public void toFile(File file, CompressionType compression) throws IOException {
        DataOutputStream out = compression == CompressionType.GZIP
                ? new DataOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file))))
                : new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

        this.toStream(out);
        out.close();
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream w = new DataOutputStream(new BufferedOutputStream(baos));

        this.toStream(w);
        w.flush();

        return baos.toByteArray();
    }

    public String toBase64() throws IOException {
        return new String(Base64.getEncoder().encode(this.toByteArray()), StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RootTag rootTag = (RootTag) o;

        if (!Objects.equals(name, rootTag.name)) return false;

        return Objects.equals(compound, rootTag.compound);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (compound != null ? compound.hashCode() : 0);

        return result;
    }
}
