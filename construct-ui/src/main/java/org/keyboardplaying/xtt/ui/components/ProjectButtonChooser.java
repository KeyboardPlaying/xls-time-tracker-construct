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
package org.keyboardplaying.xtt.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.keyboardplaying.xtt.ui.icon.ImageSize;

/**
 * A button which opens a directory selector.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ProjectButtonChooser extends JButton {

    private static final ImageSize IMAGE_SIZE = ImageSize.W_16;

    private static final String IMAGE_NAME = "action-search-folder";

    public ProjectButtonChooser(ImageLoader images, I18nHelper i18n, ProjectLocationHelper location) {
        super(new ImageIcon(images.getImage(IMAGE_NAME, IMAGE_SIZE)));

        this.addActionListener(
                new ProjectDirectoryChooserListener(this, makeDirectoryChooser(i18n, location), location));
    }

    private JFileChooser makeDirectoryChooser(I18nHelper i18n, ProjectLocationHelper location) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(location.getProjectLocation());
        chooser.setDialogTitle(i18n.getMessage("project.directory.select"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    private static class ProjectDirectoryChooserListener implements ActionListener {

        private final JButton btn;
        private final JFileChooser chooser;

        private final ProjectLocationHelper location;

        public ProjectDirectoryChooserListener(JButton btn, JFileChooser chooser, ProjectLocationHelper location) {
            super();
            this.btn = btn;
            this.chooser = chooser;
            this.location = location;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (chooser.showOpenDialog(btn) == JFileChooser.APPROVE_OPTION) {
                location.setProjectLocation(chooser.getSelectedFile());
            }
        }
    }
}
