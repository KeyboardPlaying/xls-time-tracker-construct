package org.keyboardplaying.construct.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.keyboardplaying.construct.util.StreamCopier;

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

    private boolean zipFiles(List<File> files) throws FileNotFoundException, IOException {
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

    private List<File> addFilesInDirectory(File root, ArrayList<File> list) {
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                addFilesInDirectory(file, list);
            }
        } else {
            list.add(root);
        }
        return list;
    }

    private void addZipEntry(URI root, File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

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
