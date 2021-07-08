package dev.dewy.nbt.tags;

import java.util.Objects;

/**
 * Represents the root compound tag.
 *
 * @author dewy
 */
public class RootTag {
    private String name;
    private CompoundTag tag;

    public RootTag(String name, CompoundTag tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public CompoundTag getTag() {
        return tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RootTag rootTag = (RootTag) o;

        if (!Objects.equals(name, rootTag.name)) return false;

        return Objects.equals(tag, rootTag.tag);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);

        return result;
    }
}
