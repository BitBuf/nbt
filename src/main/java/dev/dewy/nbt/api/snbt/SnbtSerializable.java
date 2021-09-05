package dev.dewy.nbt.api.snbt;

import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.api.registry.TagTypeRegistry;

public interface SnbtSerializable {
    String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config);

    Tag fromSnbt(String snbt, int depth, TagTypeRegistry registry, SnbtConfig config);
}
