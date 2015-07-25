package org.keyboardplaying.construct.ui.components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
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

    private BufferedImage master;

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
        super(label);

        Objects.requireNonNull(imageName, "An IconButton must be provided an image name.");

        try {
            master = ImageIO
                    .read(getClass().getResource(IMG_PATH_PREFIX + imageName + IMG_EXTENSION));

            setIcon(new ImageIcon(master));
        } catch (IOException e) {
            // icons are with source, this should not happen
        }
    }
}
