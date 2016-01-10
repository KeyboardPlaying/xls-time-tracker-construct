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
package org.keyboardplaying.xtt.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * A class to manage the paths relative to the project directory.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ProjectLocationHelper {

    /** The key the location is stored under in the preferences. */
    protected static final String PROJECT_DIR_PREFKEY = "project.dir";
    /** The default location for the project if no preference exists. */
    private static final String PROJECT_DIR_DEFAULT = ".";

    /** The name of the constructed XLSX file. The file should be in the same location as the construct utility. */
    private static final String CONSTRUCT_PATH = "tracker.xlsx";
    /** The name of the exploded XLSX file. The directory should be in the same location as the construct utility. */
    private static final String DECONSTRUCT_PATH = "xlsx_deconstructed";

    /**
     * An interface for listening to project configuration updates.
     *
     * @author Cyrille Chopelet (https://keyboardplaying.org)
     */
    public static interface UpdateListener {

        /**
         * ProjectAction to perform when the configuration is updated.
         *
         * @param location
         *            the updated configuration
         * @param valid
         *            whether the updated location is valid
         */
        void notifyLocationUpdate(File location, boolean valid);
    }

    @Autowired
    private PreferencesHelper preferences;

    private List<UpdateListener> listeners = new ArrayList<>();
    private File location;

    /**
     * Sets the preferences manager for this instance.
     *
     * @param preferences
     *            the new preferences manager
     */
    public void setPreferences(PreferencesHelper preferences) {
        this.preferences = preferences;
    }

    /** Initializes this instance. */
    @PostConstruct
    public void init() {
        String path = this.preferences.get(PROJECT_DIR_PREFKEY);
        this.location = new File(path != null ? path : PROJECT_DIR_DEFAULT);
    }

    /**
     * Registers a listener to be notified when the project location is updated.
     *
     * @param listener
     *            the listener to notify
     */
    public void registerForUpdate(UpdateListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Tests if the current location is a valid project location.
     * <p/>
     * To be valid, the location must not be null, must exist and must be a directory.
     *
     * @return {@code true} if the current location is valid
     * @see #isValid(File)
     */
    public boolean isValid() {
        return isValid(getProjectLocation());
    }

    /**
     * Tests if a {@link File} is a valid project location.
     * <p/>
     * To be valid, the supplied argument must not be null, must exist and must be a directory.
     *
     * @param d
     *            the {@link File} to test
     * @return {@code true} if the supplied argument is a possible location for the project
     */
    // XXX maybe it could test if the xlsx_deconstructed dir is present
    public boolean isValid(File d) {
        return d != null && d.exists() && d.isDirectory();
    }

    /**
     * Returns the project directory.
     *
     * @return the project directory
     */
    public File getProjectLocation() {
        return location;
    }

    /**
     * Updates the project location.
     *
     * @param path
     *            the new project location
     * @throws IllegalArgumentException
     *             if the location is not valid
     * @see #isValid(File)
     */
    public void setProjectLocation(String path) {
        setProjectLocation(new File(path != null ? path : PROJECT_DIR_DEFAULT));
    }

    /**
     * Updates the project location.
     *
     * @param newLocation
     *            the new project location
     * @throws IllegalArgumentException
     *             if the location is not valid
     * @see #isValid(File)
     */
    public void setProjectLocation(File newLocation) {
        this.location = newLocation;
        if (isValid(newLocation)) {
            preferences.set(PROJECT_DIR_PREFKEY, newLocation.getAbsolutePath());
        }
        notifyListeners();
    }

    /** Notify all registered listeners that the project location was updated. */
    private void notifyListeners() {
        boolean valid = isValid();
        for (UpdateListener listener : listeners) {
            listener.notifyLocationUpdate(location, valid);
        }
    }

    /**
     * Returns the constructed XLSX file.
     *
     * @return the constructed XLSX file
     */
    public File getConstructedFile() {
        return new File(this.location, CONSTRUCT_PATH);
    }

    /**
     * Returns the directory containing the deconstructed XLSX.
     *
     * @return the deconstructed directory
     */
    public File getDeconstructedDirectory() {
        return new File(this.location, DECONSTRUCT_PATH);
    }
}
