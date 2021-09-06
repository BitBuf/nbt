package dev.dewy.nbt.api.snbt;

import dev.dewy.nbt.api.registry.TagTypeRegistry;

/**
 * Interface for SNBT serialization. Must be implemented if your tag will be SNBT serializable. Reading is not yet supported.
 *
 * @author dewy
 */
public interface SnbtSerializable {
    String toSnbt(int depth, TagTypeRegistry registry, SnbtConfig config);
}
