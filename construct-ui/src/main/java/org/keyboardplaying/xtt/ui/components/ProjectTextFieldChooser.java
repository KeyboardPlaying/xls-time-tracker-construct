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

import java.io.File;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener;

/**
 * A text field representing the directory the construct will work in.
 * <p/>
 * The value can also be edited.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ProjectTextFieldChooser extends JTextField implements DocumentListener, UpdateListener {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -1641064152036700917L;

    private ProjectLocationHelper locationHelper;

    /**
     * Creates a new instance.
     *
     * @param locationHelper the new project location helper
     */
    public ProjectTextFieldChooser(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
        updateTextField(locationHelper.getProjectLocation());
        getDocument().addDocumentListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener#notifyLocationUpdate(java.io.File,
     * boolean)
     */
    @Override
    public void notifyLocationUpdate(File location, boolean valid) {
        updateTextField(location);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        updateLocation();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        updateLocation();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        updateLocation();
    }

    /**
     * Updates the location of the project. This will trigger a "location updated event"
     */
    private void updateLocation() {
        locationHelper.setProjectLocation(getText());
    }

    /**
     * Updates the text field without firing the update event.
     *
     * @param directory the selected directory
     */
    private void updateTextField(File directory) {
        setText(directory.getAbsolutePath());
    }
}
