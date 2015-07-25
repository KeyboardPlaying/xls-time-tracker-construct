package org.keyboardplaying.construct.configuration;

import java.io.File;

/**
 * A class to manage the paths relative to the project directory.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
// TODO Javadoc
public class ProjectLocation {

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

    public ProjectLocation() {
        this(".");
    }

    public ProjectLocation(String path) {
        setRoot(new File(path));
    }

    public File getRoot() {
        return this.root == null ? new File(".") : this.root;
    }

    public void setRoot(File directory) {
        this.root = directory;
    }

    public boolean isValid() {
        File dir = getRoot();
        return dir.exists() && dir.isDirectory();
    }

    public File getConstructedFile() {
        return new File(getRoot(), CONSTRUCT_PATH);
    }

    public File getDeconstructedDirectory() {
        return new File(getRoot(), DECONSTRUCT_PATH);
    }
}
