package org.keyboardplaying.xtt.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Unzips a file into a directory.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class Unzipper {

    private File source;
    private File target;

    /**
     * Creates a new instance.
     *
     * @param source
     *            the original zip file
     * @param target
     *            the directory the file should be unzipped to
     */
    public Unzipper(File source, File target) {
        this.source = Objects.requireNonNull(source, "Source must not be null.");
        this.target = Objects.requireNonNull(target, "Target must not be null.");

        if (!source.exists() || !source.isFile()) {
            throw new IllegalArgumentException(
                    "Source " + source.getAbsolutePath() + " does not exist or is not a file.");
        }
        if (target.exists() && !target.isDirectory()) {
            throw new IllegalArgumentException(
                    "Target " + target.getAbsolutePath() + " already exists and is not a directory.");
        }
    }

    /**
     * Empties the target directory if it already exists and builds it again.
     *
     * @return {@code true} if target file was successfully built
     * @throws IOException
     *             if an error occurred at any step of the processing
     *
     * @see #makeEmptyTarget()
     * @see #buildTarget()
     */
    public boolean cleanAndBuildTarget() throws IOException {
        // Remove if existing
        if (!makeEmptyTarget()) {
            throw new IOException("The target directory could not be emptied.");
        }

        // Build anew
        return buildTarget();
    }

    /**
     * Empties the target directory while preserving the root.
     * <p/>
     * If the target directory does not exist, an empty target is created.
     *
     * @return {@code true}
     */
    public boolean makeEmptyTarget() {
        if (!target.exists()) {
            return target.mkdirs();
        }
        return delete(target, false);
    }

    private boolean delete(File file, boolean deleteRoot) {
        if (file.isDirectory()) {
            boolean result = true;
            for (File child : file.listFiles()) {
                result &= delete(child, true);
            }
            if (deleteRoot) {
                result &= file.delete();
            }
            return result;
        }
        return file.delete();
    }

    /**
     * Builds an unzipped directory from the source ZIP file.
     *
     * @return {@code true} if target file was successfully built
     * @throws IOException
     *             if an error occurred at any step of the processing
     */
    public boolean buildTarget() throws IOException {

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                unzipEntry(ze, zis);
                ze = zis.getNextEntry();
            }
        }

        return true;
    }

    private void unzipEntry(ZipEntry ze, ZipInputStream zis) throws IOException, FileNotFoundException {
        File entry = new File(target, ze.getName());

        // create all non existing directories
        File destinationDirectory = new File(entry.getParent());
        destinationDirectory.mkdirs();

        try (FileOutputStream fos = new FileOutputStream(entry)) {
            new StreamCopier().copyStream(zis, fos);
        }

        zis.closeEntry();
    }
}
