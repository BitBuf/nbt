package dev.dewy.nbt.tags;

import dev.dewy.nbt.TagTypeRegistry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class Tag {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract byte getTypeId();

    public abstract Object getValue();

    public abstract void write(DataOutput output, int depth, TagTypeRegistry registry) throws IOException;

    public abstract Tag read(DataInput input, int depth, TagTypeRegistry registry) throws IOException;
}
