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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Test;
import org.keyboardplaying.xtt.util.FileTestUtil;

/**
 * Test class for {@link Zipper}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ZipperTest {

    /** Tests zipping a directory. */
    @Test
    @SuppressWarnings("javadoc")
    public void testBuildTargetFromDirectory() throws IOException {
        testBuildTarget("xlsx_ref", "xlsx_test.zip", "xlsx_ref.xlsx");
    }

    /** Tests zipping a file. */
    @Test
    @SuppressWarnings("javadoc")
    public void testBuildTargetFromFile() throws IOException {
        testBuildTarget("xlsx_ref.xlsx", "xlsx_test.zip", "xlsx_ref.zip");
    }

    private void testBuildTarget(String in, String out, String expected) throws IOException {
        // Prepare
        File inFile = FileTestUtil.getFile(getClass(), in);
        File expFile = FileTestUtil.getFile(getClass(), expected);

        File outDir = new File("target/test");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, out);
        Zipper zipper = new Zipper(inFile, outFile);

        // Execute
        zipper.cleanAndBuildTarget();

        // Assert
        assertZipEquals(expFile, outFile);
    }

    private void assertZipEquals(File expected, File actual) throws IOException {
        try (ZipFile ze = new ZipFile(expected); ZipFile za = new ZipFile(actual)) {

            List<? extends ZipEntry> expEntries = Collections.list(ze.entries());
            List<? extends ZipEntry> actEntries = Collections.list(za.entries());

            Collections.sort(expEntries, new ZipEntryComparator());
            Collections.sort(actEntries, new ZipEntryComparator());

            assertEquals(expEntries.size(), actEntries.size());

            Iterator<? extends ZipEntry> expIter = expEntries.iterator();
            Iterator<? extends ZipEntry> actIter = actEntries.iterator();

            while (expIter.hasNext()) {
                assertTrue(actIter.hasNext());

                ZipEntry expEntry = expIter.next();
                ZipEntry actEntry = actIter.next();

                assertEquals(expEntry.getName(), actEntry.getName());
                assertEquals(expEntry.isDirectory(), actEntry.isDirectory());
                assertEquals(expEntry.getSize(), actEntry.getSize());
            }
            assertFalse(actIter.hasNext());
        }
    }

    private static final class ZipEntryComparator implements Comparator<ZipEntry> {
        @Override
        public int compare(ZipEntry e1, ZipEntry e2) {
            return e1.getName().compareTo(e2.getName());
        }
    }
}
