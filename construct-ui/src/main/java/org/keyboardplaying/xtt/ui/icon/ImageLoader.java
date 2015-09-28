package org.keyboardplaying.xtt.ui.icon;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * A utility to load the images to display as button or application icons.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ImageLoader {

    private static final String IMG_PATH_PREFIX = "tango/";
    private static final String IMG_EXTENSION = ".png";

    private BufferedImage loadImage(String imageName, IconSize size) throws IOException {
        return ImageIO.read(
                getClass().getResourceAsStream(IMG_PATH_PREFIX + size.getPath() + '/' + imageName + IMG_EXTENSION));
    }

    public Image getImage(String imageName, IconSize size) {
        try {
            return loadImage(imageName, size);
        } catch (IOException e) {
            // icons are with source, this should not happen
            return null;
        }
    }

    public List<Image> getImages(String imageName) {
        List<Image> images = new ArrayList<>();
        for (IconSize size : IconSize.values()) {
            try {
                images.add(loadImage(imageName, size));
            } catch (IOException e) {
                // OK, that one's missing, we'll do without
            }
        }
        return images;
    }
}
