package org.keyboardplaying.construct.ui.components;

import java.awt.Image;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A component to easily make a button with an icon from an image embedded within the sources.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class IconButton extends JButton {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -9085591317844409661L;

    private static final String IMG_PATH_PREFIX = "/icons/tango/16/";
    private static final String IMG_EXTENSION = ".png";

    /**
     * Creates a new instance.
     *
     * @param imageName
     *            the name of the image to use for the icon, without path nor extension
     */
    public IconButton(String imageName) {
        this(null, imageName);
    }

    /**
     * Creates a new instance.
     *
     * @param label
     *            the label for the icon
     * @param imageName
     *            the name of the image to use for the icon, without path nor extension
     */
    public IconButton(String label, String imageName) {
        super(label, makeIcon(imageName));
    }

    private static Icon makeIcon(String imageName) {
        Objects.requireNonNull(imageName, "An IconButton must be provided an image name.");

        Icon icon;
        try {
            Image img = ImageIO.read(
                    IconButton.class.getResource(IMG_PATH_PREFIX + imageName + IMG_EXTENSION));
            icon = new ImageIcon(img);
        } catch (IOException e) {
            // icons are with source, this should not happen
            icon = null;
        }
        return icon;
    }
}
