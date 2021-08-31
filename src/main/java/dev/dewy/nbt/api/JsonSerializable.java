package dev.dewy.nbt.api;

import com.google.gson.JsonObject;
import dev.dewy.nbt.registry.TagTypeRegistry;

import java.io.IOException;

public interface JsonSerializable {
    JsonObject toJson(int depth, TagTypeRegistry registry) throws IOException;

    Tag fromJson(JsonObject json, int depth, TagTypeRegistry registry) throws IOException;
}
