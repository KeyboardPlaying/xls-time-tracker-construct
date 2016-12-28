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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for file management.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public final class Files {

    private Files() {
    }

    /**
     * Lists all files in a directory recursively or returns the supplied file if not a directory.
     *
     * @param file               the root file or directory
     * @param includeDirectories {@code true} to include the directories in the list
     * @return the list of all files under the root directory, or the file if not a directory
     */
    public static List<File> listFiles(File file, boolean includeDirectories) {
        List<File> files = new ArrayList<>();
        if (file.isDirectory()) {
            if (includeDirectories) {
                files.add(file);
            }
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File f : subFiles) {
                    files.addAll(listFiles(f, includeDirectories));
                }
            }
        } else {
            files.add(file);
        }
        return files;
    }

    /**
     * Deletes a file or a directory.
     *
     * @param file the file or directory to delete
     * @return {@code true} if and only if all files or directories is successfully deleted; {@code false} otherwise
     */
    public static boolean delete(File file) {
        if (!file.exists()) {
            return true;
        }

        if (file.isFile()) {
            return file.delete();
        }

        boolean deleted = true;
        File[] subFiles = file.listFiles();
        if (subFiles != null) {
            for (File f : subFiles) {
                deleted &= delete(f);
            }
        }
        return file.delete() && deleted;
    }
}
