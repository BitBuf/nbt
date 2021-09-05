package dev.dewy.nbt.api.snbt;

import lombok.Data;

@Data
public class SnbtConfig {
    private boolean prettyPrint;
    private String indent = "    ";
    private int inlineThreshold = 100;
}
