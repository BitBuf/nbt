package dev.dewy.nbt.api.registry;

import dev.dewy.nbt.api.Tag;
import dev.dewy.nbt.tags.TagType;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry mapping {@code byte} tag type IDs to tag type classes. Used to register custom-made {@link Tag} types.
 *
 * @author dewy
 */
public class TagTypeRegistry {
    private final Map<Byte, @NonNull Class<? extends Tag>> registry = new HashMap<>();

    {
        TagType.registerAll(this);
    }

    /**
     * Register a custom-made tag type with a unique {@code byte} ID. IDs 0-12 (inclusive) are reserved and may not be used.
     *
     * @param id the tag type's unique ID used in reading and writing.
     * @param clazz the tag type class.
     * @throws TagTypeRegistryException if the ID provided is either registered already or is a reserved ID (0-12 inclusive).
     */
    public void registerTagType(byte id, @NonNull Class<? extends Tag> clazz) throws TagTypeRegistryException {
        if (id == 0) {
            throw new TagTypeRegistryException("Cannot register NBT tag type " + clazz + " with ID " + id + ", as that ID is reserved.");
        }

        if (this.registry.containsKey(id)) {
            throw new TagTypeRegistryException("Cannot register NBT tag type " + clazz + " with ID " + id + ", as that ID is already in use by the tag type " + this.registry.get(id).getSimpleName());
        }

        if (registry.containsValue(clazz)) {
            byte existing = 0;
            for (Map.Entry<Byte, Class<? extends Tag>> entry : this.registry.entrySet()) {
                if (entry.getValue().equals(clazz)) {
                    existing = entry.getKey();
                }
            }

            throw new TagTypeRegistryException("NBT tag type " + clazz.getSimpleName() + " already registered under ID " + existing);
        }

        this.registry.put(id, clazz);
    }

    /**
     * Deregister a custom-made tag type with a provided tag type ID.
     *
     * @param id the ID of the tag type to deregister.
     * @return if the tag type was deregistered successfully.
     */
    public boolean deregisterTagType(byte id)  {
        if (id >= 0 && id <= 12) {
            return false;
        }

        return this.registry.remove(id) != null;
    }

    /**
     * Deregister a custom-made tag type with a provided tag type ID and class value.
     *
     * @param id the ID of the tag type to deregister.
     * @param clazz the class value of the tag type to deregister.
     * @return if the tag type was deregistered successfully.
     */
    public boolean deregisterTagType(byte id, Class<? extends Tag> clazz) {
        return this.registry.remove(id, clazz);
    }

    /**
     * Returns a tag type class value from the registry from a provided {@code byte} ID.
     *
     * @param id the ID of the tag type to retrieve.
     * @return a tag type class value from the registry from a provided {@code byte} ID.
     */
    public Class<? extends Tag> getClassFromId(byte id) {
        return this.registry.get(id);
    }

    /**
     * Returns an empty instance of the given {@link Tag} type, with a {@code null} name and a default (possibly {@code null}) value.
     * Only use this if you really know what you're doing.
     *
     * @param clazz the tag type to instantiate.
     * @return an empty instance of the tag type provided.
     * @throws TagTypeRegistryException if a reflection error occurs when instantiating the tag.
     */
    public Tag instantiate(@NonNull Class<? extends Tag> clazz) throws TagTypeRegistryException {
        try {
            Constructor<? extends Tag> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);

            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new TagTypeRegistryException("Instance of tag type class " + clazz.getSimpleName() + " could not be created.", e);
        }
    }
}
