package org.keyboardplaying.construct.action;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * An action to remove preferences from the computer the constructor is executed upon.
 *
 * @author cyChop (http://keyboardplaying.org)
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
     * @see org.keyboardplaying.construct.action.Action#getLabel()
     */
    @Override
    public String getLabel() {
        return "Clear pref.";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getImageName()
     */
    @Override
    public String getIconImageName() {
        return "edit-clear";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "Preferences suppression failed";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() throws BackingStoreException {
        preferences.clear();
        return true;
    }
}
