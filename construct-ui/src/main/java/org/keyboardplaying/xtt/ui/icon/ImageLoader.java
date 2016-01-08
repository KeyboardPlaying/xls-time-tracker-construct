/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ImageLoader {

    private static final String IMG_PATH_PREFIX = "tango/";
    private static final String IMG_EXTENSION = ".png";

    private BufferedImage loadImage(String imageName, IconSize size) throws IOException {
        return ImageIO.read(
                getClass().getResourceAsStream(IMG_PATH_PREFIX + size.getPath() + '/' + imageName + IMG_EXTENSION));
    }

    /**
     * Loads the image in PNG format with the corresponding name and size.
     *
     * @param imageName
     *            the name of the image to load (without extension)
     * @param size
     *            the size to load
     * @return the image
     */
    public Image getImage(String imageName, IconSize size) {
        try {
            return loadImage(imageName, size);
        } catch (IOException e) {
            // icons are with source, this should not happen
            return null;
        }
    }

    /**
     * Loads the images with the corresponding name in all sizes.
     *
     * @param imageName
     *            the name of the image to load (without extension)
     * @return the images
     *
     * @see #getImage(String, IconSize)
     */
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
