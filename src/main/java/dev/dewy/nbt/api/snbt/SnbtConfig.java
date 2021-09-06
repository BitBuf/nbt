package dev.dewy.nbt.api.snbt;

import lombok.Data;

/**
 * Configuration class for SNBT serialization.
 *
 * @author dewy
 */
@Data
public class SnbtConfig {
    /**
     * Toggles SNBT pretty-printing.
     */
    private boolean prettyPrint;

    /**
     * Defines the number of spaces " " to be used to indent in pretty-printing.
     */
    private int indentSpaces = 4;

    /**
     * Defines the threshold at which array tags will be displayed inline rather than block to improve readability.
     */
    private int inlineThreshold = 100;
}
