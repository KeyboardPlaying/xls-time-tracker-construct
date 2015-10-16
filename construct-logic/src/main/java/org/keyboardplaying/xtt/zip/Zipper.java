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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zips a directory into a file.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class Zipper {

    private File source;
    private File target;

    /**
     * Creates a new instance.
     *
     * @param source
     *            the original file or directory
     * @param target
     *            the destination file
     *
     * @throws IllegalArgumentException
     *             if the target already exists and is a directory
     */
    public Zipper(File source, File target) {
        this.source = Objects.requireNonNull(source, "Source must not be null.");
        this.target = Objects.requireNonNull(target, "Target must not be null.");

        if (!source.exists()) {
            // Do not control if the source is a directory, the code will be reusable
            throw new IllegalArgumentException("Source " + source.getAbsolutePath() + " does not exist.");
        }
        if (target.exists() && target.isDirectory()) {
            throw new IllegalArgumentException(
                    "Target " + target.getAbsolutePath() + " already exists and is a directory.");
        }
    }

    /**
     * Removes the target file if it already exists and builds it again.
     *
     * @return {@code true} if target file was successfully built
     * @throws IOException
     *             if an error occurred at any step of the processing
     *
     * @see #deleteTarget()
     * @see #buildTarget()
     */
    public boolean cleanAndBuildTarget() throws IOException {
        // Remove if existing
        if (!deleteTarget()) {
            throw new IOException("The target could not be deleted.");
        }

        // Build anew
        return buildTarget();
    }

    /**
     * Deletes the existing version of the target if any.
     *
     * @return {@code true} if the target did not exist or was successfully deleted; {@code false} otherwise
     */
    public boolean deleteTarget() {
        if (target.exists()) {
            return target.delete();
        }
        return true;
    }

    /**
     * Builds a zip file from the source file or directory.
     * <p/>
     * If the {@code source} is a directory, it will not be visible in the zip.
     *
     * @return {@code true} if target file was successfully built
     * @throws IOException
     *             if an error occurred at any step of the processing
     */
    public boolean buildTarget() throws IOException {
        List<File> files = listSourceFiles();

        return zipFiles(files);
    }

    private boolean zipFiles(List<File> files) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(target); ZipOutputStream zos = new ZipOutputStream(fos)) {
            URI root = (source.isDirectory() ? source : source.getParentFile()).toURI();
            for (File file : files) {
                addZipEntry(root, file, zos);
            }
        }

        return true;
    }

    /**
     * Creates a list of all files present under {@code source}, or {@code source} only if source is a file.
     *
     * @return a list of all files present under {@code source}
     */
    public List<File> listSourceFiles() {
        return addFilesInDirectory(source, new ArrayList<File>());
    }

    private List<File> addFilesInDirectory(File root, List<File> list) {
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                addFilesInDirectory(file, list);
            }
        } else {
            list.add(root);
        }
        return list;
    }

    private void addZipEntry(URI root, File file, ZipOutputStream zos) throws IOException {

        try (FileInputStream fis = new FileInputStream(file)) {

            // Make the entry's path relative to the zip root
            String relativePath = root.relativize(file.toURI()).getPath();

            // Create entry and copy content
            ZipEntry zipEntry = new ZipEntry(relativePath);
            zos.putNextEntry(zipEntry);
            new StreamCopier().copyStream(fis, zos);
            zos.closeEntry();
        }
    }
}
