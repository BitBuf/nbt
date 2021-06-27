package dev.dewy.nbt.utils;

/**
 * Defines the types of compression to be used for NBT data.
 *
 * @author dewy
 */
public enum CompressionType {
    /**
     * No compression.
     */
    NONE,

    /**
     * GZIP compression (adaptive Lempel-Ziv coding, deflate mode).
     */
    GZIP
}
