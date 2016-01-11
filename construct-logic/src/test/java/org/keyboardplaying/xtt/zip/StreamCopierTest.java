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
package org.keyboardplaying.xtt.zip;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.junit.Test;
import org.keyboardplaying.xtt.util.FileTestUtil;

/**
 * Test class for {@link StreamCopier}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class StreamCopierTest {

    private static final String TEST_FILE_NAME = "lipsum.txt";

    /** Tests {@link StreamCopier#copyStream(InputStream, java.io.OutputStream)} when no buffer has been specified. */
    @Test
    @SuppressWarnings("javadoc")
    public void testCopyStream() throws IOException {
        testCopy(new StreamCopier());
    }

    /** Tests {@link StreamCopier#copyStream(InputStream, java.io.OutputStream)} when a buffer has been specified. */
    @Test
    @SuppressWarnings("javadoc")
    public void testCopyStreamWithSpecifiedBuffer() throws IOException {
        testCopy(new StreamCopier(256));
    }

    private void testCopy(StreamCopier copier) throws IOException {
        // Prepare
        String path = FileTestUtil.getPath(getClass(), TEST_FILE_NAME);
        File file = new File(path);
        byte[] parsed;

        // Execute
        try (InputStream in = new FileInputStream(file); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            copier.copyStream(in, out);
            parsed = out.toByteArray();
        }

        // Assert
        assertEquals(file.length(), parsed.length);
        assertArrayEquals(Files.readAllBytes(file.toPath()), parsed);
    }
}
