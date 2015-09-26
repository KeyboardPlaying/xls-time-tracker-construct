package org.keyboardplaying.xtt.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility to perform stream copy.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class StreamCopier {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    private int bufferSize;

    /** Creates a new instance with the default buffer size. */
    public StreamCopier() {
        this(DEFAULT_BUFFER_SIZE);
    }

    /**
     * Creates a new instance.
     *
     * @param bufferSize
     *            the buffer size
     */
    public StreamCopier(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /**
     * Copy bytes from an {@link InputStream} to an {@link OutputStream}.
     *
     * @param in
     *            the {@link InputStream} to read from
     * @param out
     *            the {@link OutputStream} to write to
     * @throws IOException
     *             if an I/O error occurs
     */
    public void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int n = 0; // number of bytes read for the current iteration
        while (EOF != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
        }
    }
}
