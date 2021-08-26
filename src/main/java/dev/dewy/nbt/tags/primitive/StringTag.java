package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.TagType;
import dev.dewy.nbt.TagTypeRegistry;
import dev.dewy.nbt.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class StringTag extends Tag {
    private @NonNull String value;

    public StringTag(String name, @NonNull String value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getTypeId() {
        return TagType.STRING.getId();
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException {
        output.writeUTF(this.value);
    }

    @Override
    public StringTag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException {
        this.value = input.readUTF();

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringTag stringTag = (StringTag) o;

        return Objects.equals(value, stringTag.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
