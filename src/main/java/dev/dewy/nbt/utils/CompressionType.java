package dev.dewy.nbt.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    GZIP,

    /**
     * DEFLATE compression with Zlib wrapping.
     */
    ZLIB;

    /**
     * Determines the CompressionType of an {@link InputStream}.
     *
     * @param in Input stream to determine compression of. Could be a {@link java.io.FileInputStream}.
     * @return ZLIB or GZIP if compressed with them, or NONE otherwise.
     * @throws IOException If any IO error occurs determining compression.
     */
    public static CompressionType getCompression(InputStream in) throws IOException {
        if (!in.markSupported())
            in = new BufferedInputStream(in);
        in.mark(0);

        if (in.read() == 120) {
            return ZLIB;
        }

        in.reset();
        if (in.read() == 31) {
            return GZIP;
        }

        return NONE;
    }
}
