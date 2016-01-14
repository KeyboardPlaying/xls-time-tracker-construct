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
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.keyboardplaying.xtt.util.FileTestUtil;

/**
 * Test class for {@link Unzipper}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class UnzipperTest {

    /** Tests unzipping when source does not exist. */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildTargetFromUnexistingSource() {
        File in = new File("something_useless");
        File out = new File("doesnt_matter_wont_exist");
        @SuppressWarnings("unused")
        Unzipper unzipper = new Unzipper(in, out);
    }

    /** Tests unzipping when source is a directory. */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildTargetFromDirectorySource() {
        File in = FileTestUtil.getFile(getClass(), "xlsx_ref");
        File out = new File("doesnt_matter_wont_exist");
        @SuppressWarnings("unused")
        Unzipper unzipper = new Unzipper(in, out);
    }

    /** Tests unzipping when target is an existing file. */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildTargetToNonDirectoryTarget() {
        File in = FileTestUtil.getFile(getClass(), "xlsx_ref.zip");
        File out = FileTestUtil.getFile(getClass(), "xlsx_ref.xlsx");
        @SuppressWarnings("unused")
        Unzipper unzipper = new Unzipper(in, out);
    }

    /** Tests unzipping a directory. */
    @Test
    @SuppressWarnings("javadoc")
    public void testBuildTargetFromDirectory() throws IOException {
        testBuildTarget("xlsx_ref.xlsx", "xlsx_test", "xlsx_ref");
    }

    /** Tests unzipping a file. */
    @Test
    @SuppressWarnings("javadoc")
    public void testBuildTargetFromFile() throws IOException {
        testBuildTarget("xlsx_ref.zip", "xlsx_test", "xlsx_ref.xlsx");
    }

    private void testBuildTarget(String in, String out, String expected) throws IOException {
        // Prepare
        File inFile = FileTestUtil.getFile(getClass(), in);
        File expFile = FileTestUtil.getFile(getClass(), expected);

        File outDir = new File("target/test/unzip");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, out);
        Unzipper unzipper = new Unzipper(inFile, outFile);

        // Execute
        unzipper.cleanAndBuildTarget();

        // Assert
        if (expFile.isFile()) {
            File[] files = outFile.listFiles();
            assertEquals(1, files.length);
            outFile = files[0];
        }
        assertFileEquals(expFile, outFile);
    }

    private void assertFileEquals(File expected, File actual) {
        assertEquals(actual.getName(), expected.length(), actual.length(), 1);
        if (expected.isDirectory()) {
            assertTrue(actual.getName(), actual.isDirectory());

            File[] expFiles = expected.listFiles();
            File[] actFiles = actual.listFiles();

            Arrays.sort(expFiles, new FileComparator());
            Arrays.sort(actFiles, new FileComparator());

            assertEquals(expFiles.length, actFiles.length);

            for (int i = 0; i < expFiles.length; i++) {
                File expFile = expFiles[i];
                File actFile = actFiles[i];
                assertEquals(actFile.getName(), expFile.getName(), actFile.getName());
                assertFileEquals(expFile, actFile);
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
