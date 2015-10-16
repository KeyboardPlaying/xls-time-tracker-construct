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

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.springframework.stereotype.Component;

/**
 * A class to manage the preferences of the application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Component
public class PreferencesHelper {

    private static final String NODE = "org/keyboardplaying/xtt/construct";

    private Preferences preferences;
    private boolean initialized;

    /** Creates a new instance. */
    public PreferencesHelper() {
        Preferences prefRoot = Preferences.userRoot();
        try {
            initialized = prefRoot.nodeExists(NODE);
        } catch (BackingStoreException e) {
            initialized = false;
        }
        preferences = prefRoot.node(NODE);
    }

    /**
     * Returns {@code true} if preferences were already initialized for this application.
     *
     * @return {@code true} if preferences were already initialized, {@code false} otherwise
     */
    public boolean wasInitialized() {
        return initialized;
    }

    /**
     * Returns the value associated with the specified key in this preference node. Returns the specified default if
     * there is no value associated with the key, or the backing store is inaccessible.
     *
     * @param key
     *            key whose associated value is to be returned
     * @return the value associated with <tt>key</tt>, or {@code null} if no value is associated with <tt>key</tt>, or
     *         the backing store is inaccessible
     * @see Preferences#get(String, String)
     */
    public String get(String key) {
        return preferences.get(key, null);
    }

    /**
     * Associates the specified value with the specified key in the preference node.
     *
     * @param key
     *            key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key
     * @see Preferences#put(String, String)
     */
    public void set(String key, String value) {
        preferences.put(key, value);
    }

    /**
     * Removes all the preferences for the XTT construct.
     *
     * @throws BackingStoreException
     *             if this operation cannot be completed due to a failure in the backing store, or inability to
     *             communicate with it.
     * @see Preferences#clear()
     * @see Preferences#removeNode()
     */
    public void clear() throws BackingStoreException {
        preferences.clear();
        preferences.removeNode();
    }
}
