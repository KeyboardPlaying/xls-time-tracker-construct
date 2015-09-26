package org.keyboardplaying.xtt.ui.components;

import java.awt.Image;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.keyboardplaying.xtt.ui.icon.IconSize;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;

/**
 * A component to easily make a button with an icon from an image embedded within the sources.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class IconButton extends JButton {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 7062712805719944391L;

    private static final IconSize IMG_SIZE = IconSize._16;

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

        Image img = new ImageLoader().getImage(imageName, IMG_SIZE);
        setIcon(new ImageIcon(img));
    }
}
