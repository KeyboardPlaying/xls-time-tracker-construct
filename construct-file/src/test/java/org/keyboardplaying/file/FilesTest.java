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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.keyboardplaying.AbstractFilesTest;

/**
 * Test class for {@link Files}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class FilesTest extends AbstractFilesTest {

    /** Tests {@link Files#listFiles(File, boolean)} when the supplied {@link File} is a file. */
    @Test
    public void testListFilesWithFile() {
        // Prepare
        File root = getFile("files/list/dir1/file11.txt");
        List<File> exp = new ArrayList<>();
        exp.add(root);

        // Execute
        List<File> files = Files.listFiles(root, true);

        // Assert
        assertEquals(exp, files);
    }

    /**
     * Tests {@link Files#listFiles(File, boolean)} when the supplied {@link File} is a directory, including
     * directories.
     */
    @Test
    public void testListFilesWithoutDirectories() {
        // Prepare
        File root = getFile("files/list");
        List<File> exp = new ArrayList<>();
        exp.add(getFile("files/list/dir1/file11.txt"));
        exp.add(getFile("files/list/dir1/file12.txt"));
        exp.add(getFile("files/list/dir2/file21.txt"));

        // Execute
        List<File> files = Files.listFiles(root, false);

        // Assert
        assertEquals(exp, files);
    }

    /**
     * Tests {@link Files#listFiles(File, boolean)} when the supplied {@link File} is a directory, not including
     * directories.
     */
    @Test
    public void testListFilesWithDirectories() {
        // Prepare
        File root = getFile("files/list");
        List<File> exp = new ArrayList<>();
        exp.add(root);
        exp.add(getFile("files/list/dir1"));
        exp.add(getFile("files/list/dir1/file11.txt"));
        exp.add(getFile("files/list/dir1/file12.txt"));
        exp.add(getFile("files/list/dir2"));
        exp.add(getFile("files/list/dir2/file21.txt"));

        // Execute
        List<File> files = Files.listFiles(root, true);

        // Assert
        assertEquals(exp, files);
    }

    /** Tests deleting an unexisting file. */
    @Test
    public void testDeleteUnexisting() {
        // Prepare
        File file = new File("nawak");

        // Execute
        boolean deleted = Files.delete(file);

        // Assert
        assertTrue(deleted);
        assertFalse(file.exists());
    }

    /** Tests deleting a file. */
    @Test
    public void testDeleteFile() {
        // Prepare
        File file = getFile("files/delete/delete.txt");

        // Execute
        boolean deleted = Files.delete(file);

        // Assert
        assertTrue(deleted);
        assertFalse(file.exists());
    }

    /** Tests deleting a directory. */
    @Test
    public void testDeleteDirectory() {
        // Prepare
        File dir = getFile("files/delete/delete");

        // Execute
        boolean deleted = Files.delete(dir);

        // Assert
        assertTrue(deleted);
        assertFalse(dir.exists());
    }
}
