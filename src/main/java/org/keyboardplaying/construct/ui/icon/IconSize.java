package org.keyboardplaying.construct.ui.icon;

/**
 * The available sizes for icons.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public enum IconSize {

    _16("16"), _22("22"), _32("32"), _64("64"), _128("128");

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
