package org.keyboardplaying.construct.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;

/**
 * @author cyChop (http://keyboardplaying.org)
 */
public class ConstructAction implements Action {

    private ProjectConfiguration project;

    public ConstructAction(ProjectConfiguration project) {
        this.project = project;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getLabel()
     */
    @Override
    public String getLabel() {
        return "Construct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ProjectConfiguration.CONSTRUCT_PATH
                + " locked?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() throws FileNotFoundException, IOException {
        File constructed = project.getConstructedFile();
        File deconstructed = project.getDeconstructedDirectory();

        // Remove previous build if any
        if (constructed.exists()) {
            constructed.delete();
        }

        // Zip deconstructed version
        zipDirectory(deconstructed, constructed);

        return true;
    }

    public List<File> listFiles(File dir) {
        return addFiles(dir, new ArrayList<File>());
    }

    private List<File> addFiles(File dir, List<File> list) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                addFiles(file, list);
            } else {
                list.add(file);
            }
        }
        return list;
    }

    public void zipDirectory(File dir, File destination) throws FileNotFoundException, IOException {
        List<File> files = listFiles(dir);

        try (FileOutputStream fos = new FileOutputStream(destination);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (File file : files) {
                addToZip(dir, file, zos);
            }
        }
    }

    private void addToZip(File zipRoot, File file, ZipOutputStream zos)
            throws FileNotFoundException, IOException {

        try (FileInputStream fis = new FileInputStream(file)) {

            // Make the entry's path relative to the zip root
            String zipFilePath = zipRoot.toURI().relativize(file.toURI()).getPath();
            ZipEntry zipEntry = new ZipEntry(zipFilePath);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }

            zos.closeEntry();
        }
    }
}
