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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener;
import org.keyboardplaying.xtt.util.FileTestUtil;

/**
 * Test class for {@link ProjectLocationHelper}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ProjectLocationHelperTest {

    private ProjectLocationHelper location;
    private PreferencesHelper prefs;

    /** Initializes the {@link ProjectLocationHelper} with a path to the dedicated test directory. */
    @Before
    public void init() {
        location = new ProjectLocationHelper();
        prefs = mock(PreferencesHelper.class);
        when(prefs.get(ProjectLocationHelper.PROJECT_DIR_PREFKEY))
                .thenReturn(FileTestUtil.getPath(getClass(), "location"));
        location.setPreferences(prefs);
        location.init();
    }

    /** Tests the initialization when no preferred location is stored. */
    @Test
    public void testDefaultLocation() {
        // Prepare
        ProjectLocationHelper defaultLocation = new ProjectLocationHelper();
        when(prefs.get(ProjectLocationHelper.PROJECT_DIR_PREFKEY)).thenReturn(null);
        defaultLocation.setPreferences(prefs);

        // Execute
        defaultLocation.init();

        // Assert
        assertEquals(new File("."), defaultLocation.getProjectLocation());
    }

    /** Tests {@link ProjectLocationHelper#isValid(java.io.File)}. */
    @Test
    public void testIsValid() {
        /* Test standard */
        assertTrue(location.isValid());

        File f;

        /* Test null case */
        assertFalse(location.isValid(null));

        /* Test not existing case */
        f = new File("somewhere/that/does/not/exist");
        assert !f.exists();
        assertFalse(location.isValid(f));

        /* Test existing but not directory */
        f = new File(FileTestUtil.getFile(getClass(), getClass().getSimpleName() + ".class").getPath());
        assert f.exists() && f.isFile();
        assertFalse(location.isValid(f));

        /* Test existing directory */
        f = f.getParentFile();
        assert f.exists() && f.isDirectory();
        assertTrue(location.isValid(f));
    }

    /** Tests {@link ProjectLocationHelper#setProjectLocation(String)} with a specified path. */
    @Test
    public void testSetProjectLocationFromCorrectPath() {
        testSetProjectLocationFromPath("some/path");
    }

    /** Tests {@link ProjectLocationHelper#setProjectLocation(String)} with a {@code null} path. */
    @Test
    public void testSetProjectLocationFromNullPath() {
        testSetProjectLocationFromPath(null);
    }

    private void testSetProjectLocationFromPath(String path) {
        // Prepare
        ProjectLocationHelper spy = spy(location);

        // Execute
        spy.setProjectLocation(path);

        // Assert
        verify(spy).setProjectLocation(new File(path == null ? "." : path));
    }

    /** Tests {@link ProjectLocationHelper#setProjectLocation(File)} with a correct file. */
    @Test
    public void testSetProjectLocationFromCorrectFile() {
        testSetProjectLocationFromFile(location.getProjectLocation());
    }

    /** Tests {@link ProjectLocationHelper#setProjectLocation(File)} with an incorrect file. */
    @Test
    public void testSetProjectLocationFromIncorrectFile() {
        testSetProjectLocationFromFile(new File("some/path"));
    }

    /** Tests {@link ProjectLocationHelper#setProjectLocation(File)} with a {@code null} file. */
    @Test
    public void testSetProjectLocationFromNullFile() {
        testSetProjectLocationFromFile(null);
    }

    private void testSetProjectLocationFromFile(File loc) {
        // Execute
        location.setProjectLocation(loc);

        // Assert
        assertEquals(loc, location.getProjectLocation());
        if (location.isValid(loc)) {
            verify(prefs, times(1)).set(ProjectLocationHelper.PROJECT_DIR_PREFKEY, loc.getAbsolutePath());
        } else {
            verify(prefs, times(0)).set(eq(ProjectLocationHelper.PROJECT_DIR_PREFKEY), any(String.class));
        }
    }

    /** Tests the notification of registered {@link UpdateListener} objects. */
    @Test
    public void testListenerNotification() {
        // Prepare
        UpdateListener listener = mock(UpdateListener.class);
        File newLocation = new File(".");
        location.registerForUpdate(listener);

        // Execute
        location.setProjectLocation(newLocation);

        // Assert
        verify(listener).notifyLocationUpdate(newLocation, true);
    }

    /** Tests {@link ProjectLocationHelper#getConstructedFile()}. */
    @Test
    public void testGetConstructedFile() {
        // Execute
        File f = location.getConstructedFile();

        // Assert
        assertEquals(location.getProjectLocation(), f.getParentFile());
        assertTrue(f.getPath().endsWith(".xlsx"));
    }

    /** Tests {@link ProjectLocationHelper#getDeconstructedDirectory()}. */
    @Test
    public void testGetDeconstructedDirectory() {
        // Execute
        File f = location.getDeconstructedDirectory();

        // Assert
        assertEquals(location.getProjectLocation(), f.getParentFile());
        assertTrue(f.isDirectory());
    }
}
