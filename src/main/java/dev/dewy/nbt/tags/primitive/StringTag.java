package dev.dewy.nbt.tags.primitive;

import dev.dewy.nbt.Tag;
import dev.dewy.nbt.TagRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class StringTag extends Tag {
    private @NonNull String value;

    public StringTag(String name, @NonNull String value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public byte getId() {
        return 8;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

    @Override
    public void write(DataOutput output, int depth, TagRegistry registry) throws IOException {
        output.writeUTF(this.value);
    }

    @Override
    public StringTag read(DataInput input, int depth, TagRegistry registry) throws IOException {
        this.value = input.readUTF();

        return this;
    }
}