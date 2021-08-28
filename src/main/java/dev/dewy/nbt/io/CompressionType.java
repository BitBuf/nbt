package dev.dewy.nbt.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Defines the types of compression supported by this library for NBT data.
 *
 * @author dewy
 */
public enum CompressionType {
    /**
     * No compression.
     */
    NONE,

    /**
     * GZIP compression ({@code GZIPInputStream} and {@code GZIPOutputStream}).
     */
    GZIP,

    /**
     * ZLIB compression ({@code InflaterInputStream} and {@code DeflaterOutputStream}).
     */
    ZLIB;

    public static CompressionType getCompression(InputStream in) throws IOException {
        if (!in.markSupported()) {
            in = new BufferedInputStream(in);
        }
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
