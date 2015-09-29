package org.keyboardplaying.xtt.ui.action;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A wrapper for the {@link ClearPrefsAction}.
 * <p/>
 * This adds a confirmation dialog and closes the application after clearing the preferences.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConfirmClearPrefsAction implements ConstructUtilityAction {

    private I18nHelper i18nHelper;
    private ClearPrefsAction clearPrefsAction;

    /**
     * Sets the i18nHelper for this instance.
     *
     * @param i18nHelper
     *            the new i18nHelper
     */
    @Autowired
    public void setI18nHelper(I18nHelper i18nHelper) {
        this.i18nHelper = i18nHelper;
    }

    /**
     * Sets the clearPrefsAction for this instance.
     *
     * @param clearPrefsAction
     *            the new clearPrefsAction
     */
    @Autowired
    public void setClearPrefsAction(ClearPrefsAction clearPrefsAction) {
        this.clearPrefsAction = clearPrefsAction;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return clearPrefsAction.getUnsuccessMessage();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#perform()
     */
    @Override
    public boolean perform() throws Exception {
        int confirm = JOptionPane.showConfirmDialog(null, i18nHelper.getMessage("warning.prefs.clear.close"),
                "Are you sure?", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clearPrefsAction.perform();
            disposeAllWindows();
        }
        return true;
    }

    /** Calls {@link Frame#dispose()} for all windows currently existing in the application. */
    private void disposeAllWindows() {
        Frame[] windows = JFrame.getFrames();
        for (Frame window : windows) {
            window.dispose();
        }
    }
}
