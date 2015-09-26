package org.keyboardplaying.xtt.ui.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A helper for internationalization.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class I18nHelper {

    private static final String BUNDLE_BASE_NAME = "org.keyboardplaying.xtt.ui.i18n.Messages";

    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, Locale.getDefault());

    /**
     * Gets the string message corresponding to the key.
     *
     * @param key
     *            the key for the desired string
     * @return the string for the given key
     */
    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
