package org.keyboardplaying.xtt.configuration;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * A class to manage the preferences of the application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class PreferencesHelper {

    private static final String APP_NAME = "xtt-construct";

    private Preferences preferences = Preferences.userRoot().node(APP_NAME);

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
