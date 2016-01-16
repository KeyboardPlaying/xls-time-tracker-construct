/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilities for stream management.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public final class Streams {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    private Streams() {
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
    public static void copy(InputStream in, OutputStream out) throws IOException {
        copy(in, out, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy bytes from an {@link InputStream} to an {@link OutputStream}.
     *
     * @param in
     *            the {@link InputStream} to read from
     * @param out
     *            the {@link OutputStream} to write to
     * @param bufferSize
     *            the size to use for the buffer when copying
     * @throws IOException
     *             if an I/O error occurs
     */
    public static void copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int n = 0; // number of bytes read for the current iteration
        while (EOF != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
        }
    }
}
