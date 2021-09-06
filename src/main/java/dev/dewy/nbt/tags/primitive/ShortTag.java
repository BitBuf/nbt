package dev.dewy.nbt.tags.primitive;

import com.google.gson.JsonObject;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.snbt.SnbtConfig;
import dev.dewy.nbt.tags.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The short tag (type ID 2) is used for storing a 16-bit signed two's complement integer; a Java primitive {@code short}.
 *
 * @author dewy
 */
@NoArgsConstructor
@AllArgsConstructor
public class ShortTag extends NumericalTag<Short> {
    private short value;

    /**
     * Constructs a short tag with a given value.
     *
     * @param value the tag's {@code Number} value, to be converted to {@code short}.
     */
    public ShortTag(@NonNull Number value) {
        this(null, value);
    }

    /**
     * Constructs a short tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code Number} value, to be converted to {@code short}.
     */
    public ShortTag(String name, @NonNull Number value) {
        this(name, value.shortValue());
    }

    /**
     * Constructs a short tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code short} value.
     */
    public ShortTag(String name, short value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.SHORT.getId();
    }

    @Override
    public Short getValue() {
        return this.value;
    }

    /**
     * Sets the {@code short} value of this short tag.
     *
     * @param value new {@code short} value to be set.
     */
    public void setValue(short value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeShort(this.value);
    }

    @Override
    public ShortTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = input.readShort();

        return this;
    }

    @Override
    public String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config) {
        return this.value + "s";
    }

    @Override
    public JsonObject toJson(int depth, TagTypeRegistry registry) {
        JsonObject json = new JsonObject();
        json.addProperty("type", this.getTypeId());

        if (this.getName() != null) {
            json.addProperty("name", this.getName());
        }

        json.addProperty("value", this.value);

        return json;
    }

    @Override
    public ShortTag fromJson(JsonObject json, int depth, TagTypeRegistry registry) {
        if (json.has("name")) {
            this.setName(json.getAsJsonPrimitive("name").getAsString());
        } else {
            this.setName(null);
        }

        this.value = json.getAsJsonPrimitive("value").getAsShort();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortTag shortTag = (ShortTag) o;

        return value == shortTag.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
