package org.keyboardplaying.xtt.ui.icon;

/**
 * The available sizes for icons.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public enum IconSize {

    /** 16px wide. */
    _16("16"),

    /** 22px wide. */
    _22("22"),

    /** 32px wide. */
    _32("32"),

    /** 64px wide. */
    _64("64"),

    /** 128px wide. */
    _128("128");

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
