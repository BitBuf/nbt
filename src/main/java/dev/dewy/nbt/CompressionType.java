package dev.dewy.nbt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public enum CompressionType {
    NONE,
    GZIP,
    ZLIB;

    static CompressionType getCompression(InputStream in) throws IOException {
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
