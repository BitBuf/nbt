package dev.dewy.nbt.tags.array;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.snbt.SnbtConfig;
import dev.dewy.nbt.tags.TagType;
import dev.dewy.nbt.tags.primitive.ByteTag;
import dev.dewy.nbt.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * The byte array tag (type ID 7) is used for storing {@code byte[]} arrays in NBT structures.
 * It is not stored as a list of {@link ByteTag}s.
 *
 * @author dewy
 */
@NoArgsConstructor
@AllArgsConstructor
public class ByteArrayTag extends ArrayTag<Byte> {
    private @NonNull byte[] value;

    /**
     * Constructs a byte array tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code byte[]} value.
     */
    public ByteArrayTag(String name, @NonNull byte[] value) {
        this.setName(name);
        this.setValue(value);
    }

    /**
     * Constructs an unnamed byte array tag using a {@code List<>} object.
     *
     * @param value the tag's {@code List<>} value, to be converted to a primitive {@code byte[]} array.
     */
    public ByteArrayTag(@NonNull List<Byte> value) {
        this(null, value);
    }

    /**
     * Constructs a byte array tag with a given name, using a List object to determine its {@code byte[]} value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code List<>} value, to be converted to a primitive {@code byte[]} array.
     */
    public ByteArrayTag(String name, @NonNull List<Byte> value) {
        this.setName(name);
        this.setValue(ArrayUtils.toPrimitive(value.toArray(new Byte[0])));
    }

    @Override
    public byte getTypeId() {
        return TagType.BYTE_ARRAY.getId();
    }

    @Override
    public byte[] getValue() {
        return this.value;
    }

    /**
     * Sets the {@code byte[]} value of this byte array tag.
     *
     * @param value new {@code byte[]} value to be set.
     */
    public void setValue(@NonNull byte[] value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeInt(this.value.length);
        output.write(this.value);
    }

    @Override
    public ByteArrayTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        byte[] tmp = new byte[input.readInt()];
        input.readFully(tmp);

        this.value = tmp;

        return this;
    }

    @Override
    public JsonObject toJson(int depth, TagTypeRegistry registry) throws IOException {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        json.addProperty("type", this.getTypeId());

        if (this.getName() != null) {
            json.addProperty("name", this.getName());
        }

        for (byte b : this) {
            array.add(b);
        }

        json.add("value", array);

        return json;
    }

    @Override
    public ByteArrayTag fromJson(JsonObject json, int depth, TagTypeRegistry registry) throws IOException {
        JsonArray array = json.getAsJsonArray("value");

        if (json.has("name")) {
            this.setName(json.getAsJsonPrimitive("name").getAsString());
        } else {
            this.setName(null);
        }

        this.value = new byte[array.size()];

        for (int i = 0; i < array.size(); i++) {
            this.value[i] = array.get(i).getAsByte();
        }

        return this;
    }

    @Override
    public String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config) {
        StringBuilder sb = new StringBuilder("[B;");

        if (config.isPrettyPrint()) {
            if (this.value.length < config.getInlineThreshold()) {
                sb.append('\n').append(StringUtils.multiplyIndent(depth + 1, config));
            } else {
                sb.append(' ');
            }
        }

        for (int i = 0; i < this.value.length; ++i) {
            if (i != 0) {
                if (config.isPrettyPrint()) {
                    if (this.value.length < config.getInlineThreshold()) {
                        sb.append(",\n").append(StringUtils.multiplyIndent(depth + 1, config));
                    } else {
                        sb.append(", ");
                    }
                } else {
                    sb.append(',');
                }
            }

            sb.append(this.value[i]).append('B');
        }

        if (config.isPrettyPrint() && this.value.length < config.getInlineThreshold()) {
            sb.append("\n").append(StringUtils.multiplyIndent(depth , config)).append(']');
        } else {
            sb.append(']');
        }

        return sb.toString();
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public Byte get(int index) {
        return this.value[index];
    }

    @Override
    public Byte set(int index, @NonNull Byte element) {
        return this.value[index] = element;
    }

    @Override
    public void insert(int index, @NonNull Byte... elements) {
        this.value = ArrayUtils.insert(index, this.value, ArrayUtils.toPrimitive(elements));
    }

    @Override
    public Byte remove(int index) {
        Byte previous = this.value[index];
        this.value = ArrayUtils.remove(this.value, index);

        return previous;
    }

    @Override
    public void clear() {
        this.value = new byte[0];
    }

    @Override
    public Iterator<Byte> iterator() {
        return Arrays.asList(ArrayUtils.toObject(this.value)).iterator();
    }

    @Override
    public void forEach(Consumer<? super Byte> action) {
        Arrays.asList(ArrayUtils.toObject(this.value)).forEach(action);
    }

    @Override
    public Spliterator<Byte> spliterator() {
        return Arrays.asList(ArrayUtils.toObject(this.value)).spliterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteArrayTag that = (ByteArrayTag) o;

        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
