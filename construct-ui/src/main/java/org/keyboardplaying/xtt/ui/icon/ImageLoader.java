package org.keyboardplaying.xtt.ui.icon;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * A utility to load the images to display as button or application icons.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
// TODO Javadoc
public class ImageLoader {

    private static final String IMG_PATH_PREFIX = "tango/";
    private static final String IMG_EXTENSION = ".png";

    public Image getImage(String imageName, IconSize size) {
        try {
            return ImageIO.read(
                    getClass().getResourceAsStream(IMG_PATH_PREFIX + size.getPath() + '/' + imageName + IMG_EXTENSION));
        } catch (IOException e) {
            // icons are with source, this should not happen
            return null;
        }
    }

    public List<Image> getImages(String imageName) {
        List<Image> images = new ArrayList<>();
        for (IconSize size : IconSize.values()) {
            images.add(getImage(imageName, size));
        }
        return images;
    }
}
