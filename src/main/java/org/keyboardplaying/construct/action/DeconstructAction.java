package org.keyboardplaying.construct.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;

/**
 * Updates the exploded version of the tracker based on the XLSX file.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class DeconstructAction implements Action {

    private ProjectConfiguration project;

    /**
     * Creates a new instance.
     *
     * @param project
     *            the project configuration
     */
    public DeconstructAction(ProjectConfiguration project) {
        this.project = project;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getLabel()
     */
    @Override
    public String getLabel() {
        return "Deconstruct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getImageName()
     */
    @Override
    public String getImageName() {
        return "package-x-generic";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file "
                + ProjectConfiguration.CONSTRUCT_PATH + " missing or the directory "
                + ProjectConfiguration.DECONSTRUCT_PATH + "?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() throws FileNotFoundException, IOException {
        File deconstructed = project.getDeconstructedDirectory();
        File constructed = project.getConstructedFile();

        // Remove previous version and create a new directory
        if (deconstructed.exists()) {
            delete(deconstructed);
        }

        // Unzip XLSX
        unzip(constructed, deconstructed);

        return true;
    }

    public void delete(File f) {

        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                delete(file);
            }
        } else {
            f.delete();
        }
    }

    public void unzip(File zip, File destination) throws FileNotFoundException, IOException {

        if (!destination.exists()) {
            destination.mkdir();
        }

        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                File entry = new File(destination, ze.getName());

                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                new File(entry.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(entry)) {

                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }

                }
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        }
    }
}
