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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.keyboardplaying.stream.Streams;

/**
 * Utility to unzip a file.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public final class Unzipper {

    private Unzipper() {
    }

    public static void unzip(File zip, File target, boolean extractHere) throws IOException {
        // Controls the parameters
        File out = controlParameters(zip, target, extractHere);

        // Remove if existing
        if (!(Files.delete(out) && out.mkdirs())) {
            throw new IOException("The target directory could not be emptied.");
        }

        // Build anew
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                unzipEntry(ze, zis, out);
                ze = zis.getNextEntry();
            }
        }
    }

    private static File controlParameters(File source, File target, boolean extractHere) {
        Objects.requireNonNull(source, "Source must not be null.");
        Objects.requireNonNull(target, "Target must not be null.");

        if (!source.exists() || !source.isFile()) {
            throw new IllegalArgumentException(
                    "Source " + source.getAbsolutePath() + " does not exist or is not a file.");
        }
        File out;
        if (extractHere) {
            out = target;
        } else {
            String zipName = source.getName();
            out = new File(target, zipName.substring(0, zipName.lastIndexOf('.')));
        }
        if (out.exists() && !out.isDirectory()) {
            throw new IllegalArgumentException(
                    "Target " + out.getAbsolutePath() + " already exists and is not a directory.");
        }
        return out;
    }

    private static void unzipEntry(ZipEntry ze, ZipInputStream zis, File target) throws IOException {
        File entry = new File(target, ze.getName());

        // create all non existing directories
        File destinationDirectory = new File(entry.getParent());
        destinationDirectory.mkdirs();

        try (FileOutputStream fos = new FileOutputStream(entry)) {
            Streams.copy(zis, fos);
        }

        zis.closeEntry();
    }
}
