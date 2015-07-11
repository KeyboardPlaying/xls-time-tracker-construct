package org.keyboardplaying.construct;

/**
 * The paths to constructed and deconstructed versions of the document.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public final class ConstructPaths {

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

    private ConstructPaths() {
    }
}
