package org.keyboardplaying.xtt.action;

import java.util.prefs.BackingStoreException;

import org.keyboardplaying.xtt.configuration.PreferencesHelper;

/**
 * An action to remove preferences from the computer the constructor is executed upon.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ClearPrefsAction implements ConstructUtilityAction {

    private PreferencesHelper preferencesHelper;

    /**
     * Sets the preferences helper for this instance.
     *
     * @param preferencesHelper
     *            the new preferences helper
     */
    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "Preferences suppression failed";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public boolean perform() throws BackingStoreException {
        // TODO this should close the application
        preferencesHelper.clear();
        return true;
    }
}
