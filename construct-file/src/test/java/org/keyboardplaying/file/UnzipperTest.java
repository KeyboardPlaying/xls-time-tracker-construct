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
import java.util.List;

import org.junit.Test;
import org.keyboardplaying.AbstractFilesTest;

/**
 * Test class for {@link Unzipper}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class UnzipperTest extends AbstractFilesTest {

    private static final String TEST_DIR = "target/test/unzip";
    private static final String DIR = "zip/";
    private static final int TOLERANCE = 1;

    /**
     * Tests unzipping when source does not exist.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("javadoc")
    public void testUnzipUnexisting() throws IOException {
        // Prepare
        File in = new File("something_useless");
        File out = new File("doesnt_matter_wont_exist");
        // Execute
        Unzipper.unzip(in, out, true);
    }

    /**
     * Tests unzipping when source is a directory.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("javadoc")
    public void testUnzipDirectory() throws IOException {
        // Prepare
        File in = getFile(DIR + "xlsx_ref");
        File out = new File("doesnt_matter_wont_exist");
        // Execute
        Unzipper.unzip(in, out, true);
    }

    /**
     * Tests unzipping when target is an existing file.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("javadoc")
    public void testUnzipToFileTarget() throws IOException {
        // Prepare
        File in = getFile(DIR + "xlsx_ref.xlsx.zip");
        File out = getFile(DIR + "xlsx_ref.xlsx");

        // Execute
        Unzipper.unzip(in, out, true);
    }

    /**
     * Tests unzipping when the deletion of the target directory fails.
     */
    @Test(expected = IOException.class)
    @SuppressWarnings("javadoc")
    public void testUnremovableTarget() throws IOException {
        // Prepare
        File in = getFile(DIR + "xlsx_ref.zip");
        File out = mock(File.class);
        when(out.exists()).thenReturn(true);
        when(out.isDirectory()).thenReturn(true);
        when(out.listFiles()).thenReturn(new File[0]);
        when(out.delete()).thenReturn(false);

        // Execute
        Unzipper.unzip(in, out, true);
    }

    /**
     * Tests unzipping a directory.
     */
    @Test
    @SuppressWarnings("javadoc")
    public void testUnzipOriginalDirectory() throws IOException {
        testUnzip("xlsx_ref.xlsx", "xlsx_test", true, "xlsx_ref");
    }

    /**
     * Tests unzipping a directory.
     */
    @Test
    @SuppressWarnings("javadoc")
    public void testUnzipOriginalDirectoryWithDirs() throws IOException {
        testUnzip("xlsx_ref.zip", "xlsx_test", true, "xlsx_ref");
    }

    /**
     * Tests unzipping a directory.
     */
    @Test
    @SuppressWarnings("javadoc")
    public void testUnzipOriginalDirectoryWithRootAndDirs() throws IOException {
        // FIXME make this one work
        testUnzip("xlsx_ref.root.zip", "", false, "xlsx_ref.root/xlsx_ref", "xlsx_ref");
    }

    /**
     * Tests unzipping a file.
     */
    @Test
    @SuppressWarnings("javadoc")
    public void testUnzipOriginalFileInPlace() throws IOException {
        testUnzip("xlsx_ref.xlsx.zip", "", true, "xlsx_ref.xlsx", "xlsx_ref.xlsx");
    }

    /**
     * Tests unzipping a file but not in place.
     */
    @Test
    @SuppressWarnings("javadoc")
    public void testUnzipOriginalFile() throws IOException {
        testUnzip("xlsx_ref.xlsx.zip", "bis", false, "bis/xlsx_ref.xlsx/xlsx_ref.xlsx", "xlsx_ref.xlsx");
    }

    private void testUnzip(String in, String out, boolean extractHere, String expected) throws IOException {
        testUnzip(in, out, extractHere, out, expected);
    }

    private void testUnzip(String in, String out, boolean extractHere, String expectedOut, String expected)
            throws IOException {
        // Prepare
        File inFile = getFile(DIR + in);
        File expFile = getFile(DIR + expected);

        File outDir = new File(TEST_DIR);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, out);

        // Execute
        Unzipper.unzip(inFile, outFile, extractHere);

        // Assert
        assertFileEquals(expFile, new File(outDir, expectedOut));
    }

    private void assertFileEquals(File expected, File actual) {
        assertEquals(actual.getAbsolutePath(), expected.length(), actual.length(), TOLERANCE);
        if (expected.isDirectory()) {
            assertTrue(actual.getName(), actual.isDirectory());

            List<File> actFiles = Files.listFiles(actual, true);
            List<File> expFiles = Files.listFiles(expected, true);

            FileComparator comp = new FileComparator();
            actFiles.sort(comp);
            expFiles.sort(comp);

            assertEquals(actFiles.size(), expFiles.size());
            for (int i = 1; i < actFiles.size(); i++) {
                File expFile = expFiles.get(i);
                File actFile = actFiles.get(i);
                assertEquals(expFile.getName(), actFile.getName());
                assertEquals(expFile.length(), actFile.length(), TOLERANCE);
            }
        } else {
            assertFalse(actual.getName(), actual.isDirectory());
        }
    }

    private static final class FileComparator implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
        }
    }
}
