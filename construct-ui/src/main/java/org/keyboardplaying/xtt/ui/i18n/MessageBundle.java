package org.keyboardplaying.xtt.ui.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
// TODO Javadoc
public class MessageBundle {

    private static final String BUNDLE_BASE_NAME = "org.keyboardplaying.xtt.ui.i18n.Messages";
    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, Locale.getDefault());

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
