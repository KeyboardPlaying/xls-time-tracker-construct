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
package org.keyboardplaying.xtt.ui.action;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.keyboardplaying.xtt.action.ActionException;
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
     * @see org.keyboardplaying.xtt.action.Action#perform()
     */
    @Override
    public void perform() throws ActionException {
        int confirm = JOptionPane.showConfirmDialog(null, i18nHelper.getMessage("warning.prefs.clear.close"),
                "Are you sure?", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            clearPrefsAction.perform();
            disposeAllWindows();
        }
    }

    /**
     * Calls {@link Frame#dispose()} for all windows currently existing in the application.
     */
    private void disposeAllWindows() {
        Frame[] windows = JFrame.getFrames();
        for (Frame window : windows) {
            window.dispose();
        }
    }
}
