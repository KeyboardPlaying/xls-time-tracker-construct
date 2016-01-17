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
package org.keyboardplaying.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Test;
import org.keyboardplaying.AbstractFilesTest;

/**
 * Test class for {@link Files}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ZipperTest extends AbstractFilesTest {

    private static final String DIR = "zip/";

    /** Tests zipping a file that does not exist. */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("javadoc")
    public void testZipFromInexistingFile() throws IOException {
        // Prepare
        File in = new File("something_useless");
        File out = new File("doesnt_matter_wont_exist");

        // Execute
        Zipper.zip(in, out);
    }

    /** Tests zipping a file that exists and is a directory. */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("javadoc")
    public void testZipToExistingDirectory() throws IOException {
        // Prepare
        File in = getFile(DIR + "xlsx_ref.xlsx");
        File out = getFile(DIR + "xlsx_ref");

        // Execute
        Zipper.zip(in, out);
    }

    /** Tests unzipping when the deletion of the target directory fails. */
    @Test(expected = IOException.class)
    @SuppressWarnings("javadoc")
    public void testUnremovableTarget() throws IOException {
        // Prepare
        File in = getFile(DIR + "xlsx_ref.xlsx");
        File out = mock(File.class);
        when(out.exists()).thenReturn(true);
        when(out.isDirectory()).thenReturn(false);
        when(out.isFile()).thenReturn(true);
        when(out.delete()).thenReturn(false);

        // Execute
        Zipper.zip(in, out, true);
    }

    /** Tests zipping a directory. */
    @Test
    @SuppressWarnings("javadoc")
    public void testZipXlsxFromDirectory() throws IOException {
        testZip("xlsx_ref", "xlsx_test.zip", true, false, "xlsx_ref.xlsx");
    }

    @Test
    @SuppressWarnings("javadoc")
    public void testZipFromDirectoryWithDirEntries() throws IOException {
        testZip("xlsx_ref", "xlsx_test.zip", false, true, "xlsx_ref.zip");
    }

    @Test
    @SuppressWarnings("javadoc")
    public void testZipFromDirectoryWithDirAndRootEntries() throws IOException {
        testZip("xlsx_ref", "xlsx_test.zip", true, true, "xlsx_ref.root.zip");
    }

    /** Tests zipping a file. */
    @Test
    @SuppressWarnings("javadoc")
    public void testZipFromFile() throws IOException {
        testZip("xlsx_ref.xlsx", "xlsx_test.zip", false, false, "xlsx_ref.xlsx.zip");
    }

    private void testZip(String in, String out, boolean root, boolean dirs, String expected) throws IOException {
        // Prepare
        File inFile = getFile(DIR + in);
        File expFile = getFile(DIR + expected);

        File outDir = new File("target/test/zip");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, out);

        // Execute
        Zipper.zip(inFile, outFile, root, dirs);

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
                assertEquals(expEntry.getSize(), actEntry.getSize(), 1);
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
