package org.keyboardplaying.xtt.ui.icon;

/**
 * The available sizes for icons.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public enum IconSize {

    /** 16px wide. */
    W_16("16"),

    /** 22px wide. */
    W_22("22"),

    /** 32px wide. */
    W_32("32"),

    /** 64px wide. */
    W_64("64"),

    /** 128px wide. */
    W_128("128");

    private String path;

    private IconSize(String path) {
        this.path = path;
    }

    /**
     * Returns the name of the directory containing the icons for this size.
     *
     * @return the directory name
     */
    public String getPath() {
        return path;
    }
}
