package org.keyboardplaying.xtt.action;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * An action to remove preferences from the computer the constructor is executed upon.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ClearPrefsAction implements Action {

    private Preferences preferences;

    /**
     * Creates a new instance.
     *
     * @param preferences
     *            the preference manager
     */
    public ClearPrefsAction(Preferences preferences) {
        this.preferences = preferences;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "Preferences suppression failed";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#perform()
     */
    @Override
    public boolean perform() throws BackingStoreException {
        preferences.clear();
        return true;
    }
}
