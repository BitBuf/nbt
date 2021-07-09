package dev.dewy.nbt.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

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
    GZIP;

    public static boolean isGzipped(InputStream in) throws IOException {
        if (!in.markSupported()) {
            in = new BufferedInputStream(in);
        }

        in.mark(2);

        int magic;
        magic = in.read() & 0xff | ((in.read() << 8) & 0xff00);
        in.reset();

        return magic == GZIPInputStream.GZIP_MAGIC;
    }
}
