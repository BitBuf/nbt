package dev.dewy.nbt.tags.primitive;

import com.google.gson.JsonObject;
import dev.dewy.nbt.api.registry.TagTypeRegistry;
import dev.dewy.nbt.api.snbt.SnbtConfig;
import dev.dewy.nbt.tags.TagType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * The double tag (type ID 6) is used for storing a double-precision 64-bit IEEE 754 floating point value; a Java primitive {@code double}.
 *
 * @author dewy
 */
@NoArgsConstructor
@AllArgsConstructor
public class DoubleTag extends NumericalTag<Double> {
    private double value;

    /**
     * Constructs a double tag with a given name and value.
     *
     * @param name the tag's name.
     * @param value the tag's {@code double} value.
     */
    public DoubleTag(String name, double value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.DOUBLE.getId();
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    /**
     * Sets the {@code double} value of this double tag.
     *
     * @param value new {@code double} value to be set.
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeDouble(this.value);
    }

    @Override
    public DoubleTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = input.readDouble();

        return this;
    }

    @Override
    public String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config) {
        return this.value + "d";
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
    public DoubleTag fromJson(JsonObject json, int depth, TagTypeRegistry registry) {
        if (json.has("name")) {
            this.setName(json.getAsJsonPrimitive("name").getAsString());
        } else {
            this.setName(null);
        }

        this.value = json.getAsJsonPrimitive("value").getAsDouble();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoubleTag doubleTag = (DoubleTag) o;

        return Double.compare(doubleTag.value, value) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
