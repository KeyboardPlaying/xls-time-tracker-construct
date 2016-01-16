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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.keyboardplaying.stream.Streams;

/**
 * Utility to zip files.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public final class Zipper {

    private Zipper() {
    }

    /**
     * Zips a file or directory to the specified file.
     * <p/>
     * If a previous version of the destination file existed, it will be replaced. If it is a directory, an exception
     * will be thrown.
     *
     * @param in
     *            the file or directory to zip
     * @param zip
     *            the file to zip to
     * @throws IOException
     *             if an I/O error occurs
     */
    public static void zip(File in, File zip) throws IOException {
        zip(in, zip, true);
    }

    /**
     * Zips a file or directory to the specified file.
     * <p/>
     * If a previous version of the destination file existed, it will be replaced. If it is a directory, an exception
     * will be thrown.
     *
     * @param in
     *            the file or directory to zip
     * @param zip
     *            the file to zip to
     * @param includeDirectories
     *            whether directories should create entries in the zip or not
     * @throws IOException
     *             if an I/O error occurs
     */
    public static void zip(File in, File zip, boolean includeDirectories) throws IOException {
        zip(in, zip, true, includeDirectories);
    }

    /**
     * Zips a file or directory to the specified file.
     * <p/>
     * If a previous version of the destination file existed, it will be replaced. If it is a directory, an exception
     * will be thrown.
     *
     * @param in
     *            the file or directory to zip
     * @param zip
     *            the file to zip to
     * @param includeRoot
     *            whether the root level should be included or not
     * @param includeDirectories
     *            whether directories should create entries in the zip or not
     * @throws IOException
     *             if an I/O error occurs
     */
    public static void zip(File in, File zip, boolean includeRoot, boolean includeDirectories) throws IOException {
        // Check if the parameters are correct
        controlParameters(in, zip);

        // Remove if existing
        if (!Files.delete(zip)) {
            throw new IOException("The target could not be deleted.");
        }

        // Build anew
        zipFile(in, zip, includeRoot, includeDirectories);
    }

    private static void controlParameters(File in, File zip) {
        Objects.requireNonNull(in, "Source must not be null.");
        Objects.requireNonNull(zip, "Target must not be null.");

        if (!in.exists()) {
            // Do not control if the source is a directory, the code will be reusable
            throw new IllegalArgumentException("Source " + in.getAbsolutePath() + " does not exist.");
        }
        if (zip.exists() && zip.isDirectory()) {
            throw new IllegalArgumentException(
                    "Target " + zip.getAbsolutePath() + " already exists and is a directory.");
        }
    }

    private static void zipFile(File in, File zip, boolean roots, boolean dirs)
            throws IOException, FileNotFoundException {
        boolean rootIsDirectory = in.isDirectory();
        URI root = (rootIsDirectory && (!dirs || !roots) ? in : in.getParentFile()).toURI();
        List<File> files = Files.listFiles(in, dirs);
        try (FileOutputStream fos = new FileOutputStream(zip); ZipOutputStream zos = new ZipOutputStream(fos)) {
            if (rootIsDirectory && !roots) {
                files.remove(0);
            }
            for (File file : files) {
                addZipEntry(root, file, zos);
            }
        }
    }

    private static void addZipEntry(URI root, File file, ZipOutputStream zos) throws IOException {

        // Make the entry's path relative to the zip root
        String relative = root.relativize(file.toURI()).getPath();

        // Create and save entry
        ZipEntry zipEntry = new ZipEntry(relative);
        zos.putNextEntry(zipEntry);

        if (file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {

                // Copy content
                Streams.copy(fis, zos);
            }
        }
        zos.closeEntry();
    }
}
