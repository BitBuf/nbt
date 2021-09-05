package dev.dewy.nbt.api.snbt;

import lombok.Data;

@Data
public class SnbtConfig {
    private boolean prettyPrint;
    private int indentSpaces = 4;
    private int inlineThreshold = 100;
}
