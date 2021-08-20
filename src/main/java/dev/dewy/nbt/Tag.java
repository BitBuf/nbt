package dev.dewy.nbt;

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

    public abstract byte getId();

    public abstract Object getValue();

    public abstract void write(DataOutput output, int depth, TagRegistry registry) throws IOException;

    public abstract Tag read(DataInput input, int depth, TagRegistry registry) throws IOException;
}
