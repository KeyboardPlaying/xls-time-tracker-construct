package org.keyboardplaying.construct.model;

import java.io.File;

/**
 * A class to manage the paths relative to the project directory.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
// TODO Javadoc
public class ProjectConfiguration {

    /**
     * The name of the constructed XLSX file. The file should be in the same location as the
     * construct utility.
     */
    public static final String CONSTRUCT_PATH = "tracker.xlsx";

    /**
     * The name of the exploded XLSX file. The directory should be in the same location as the
     * construct utility.
     */
    public static final String DECONSTRUCT_PATH = "xlsx_deconstructed";

    private File root;

    public ProjectConfiguration() {
        this(".");
    }

    public ProjectConfiguration(String path) {
        setLocation(new File(path));
    }

    public File getLocation() {
        return this.root == null ? new File(".") : this.root;
    }

    public void setLocation(File directory) {
        this.root = directory;
    }

    public boolean isValid() {
        File dir = getLocation();
        return dir.exists() && dir.isDirectory();
    }

    public File getConstructedFile() {
        return new File(getLocation(), CONSTRUCT_PATH);
    }

    public File getDeconstructedDirectory() {
        return new File(getLocation(), DECONSTRUCT_PATH);
    }
}
