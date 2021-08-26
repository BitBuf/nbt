package dev.dewy.nbt;

import dev.dewy.nbt.exceptions.TagTypeRegistryException;
import dev.dewy.nbt.tags.Tag;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class TagTypeRegistry {
    private final Map<Byte, @NonNull Class<? extends Tag>> registry = new HashMap<>();

    {
        TagType.registerAll(this);
    }

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

    public boolean deregisterTagType(byte id) {
        return this.registry.remove(id) != null;
    }

    public boolean deregisterTagType(byte id, Class<? extends Tag> clazz) {
        return this.registry.remove(id, clazz);
    }

    public Class<? extends Tag> getClassFromId(byte id) {
        return this.registry.get(id);
    }

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
