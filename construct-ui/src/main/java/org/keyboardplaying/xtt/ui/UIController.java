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
package org.keyboardplaying.xtt.ui;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.keyboardplaying.xtt.action.Action;
import org.keyboardplaying.xtt.action.ActionException;
import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.action.ProjectAction;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.components.LocaleComboBox;
import org.keyboardplaying.xtt.ui.components.ProjectButtonChooser;
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.i18n.swing.I14edJButton;
import org.keyboardplaying.xtt.ui.i18n.swing.I14edJFrame;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.keyboardplaying.xtt.ui.icon.ImageSize;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class UIController {

    @Autowired
    private I18nHelper i18n;
    @Autowired
    private ImageLoader images;

    @Autowired
    private PreferencesHelper prefs;
    @Autowired
    private ProjectLocationHelper location;

    @Autowired
    private ConstructAction constructAction;
    @Autowired
    private DeconstructAction deconstructAction;
    @Autowired
    private ConfirmClearPrefsAction clearPrefsAction;

    private ConstructUtilityAction settingsAction = new ConstructUtilityAction() {

        @Override
        public void perform() throws ActionException {
            showSettingsWindow();
        }
    };

    /**
     * Sets the i18nHelper for this instance.
     *
     * @param i18nHelper
     *            the new i18nHelper
     */
    public void setI18nHelper(I18nHelper i18nHelper) {
        this.i18n = i18nHelper;
    }

    /**
     * Sets the imageLoader for this instance.
     *
     * @param imageLoader
     *            the new imageLoader
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.images = imageLoader;
    }

    /**
     * Sets the preferencesHelper for this instance.
     *
     * @param preferencesHelper
     *            the new preferencesHelper
     */
    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.prefs = preferencesHelper;
    }

    /**
     * Sets the locationHelper for this instance.
     *
     * @param locationHelper
     *            the new locationHelper
     */
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.location = locationHelper;
    }

    /**
     * Sets the constructAction for this instance.
     *
     * @param constructAction
     *            the new constructAction
     */
    public void setConstructAction(ConstructAction constructAction) {
        this.constructAction = constructAction;
    }

    /**
     * Sets the deconstructAction for this instance.
     *
     * @param deconstructAction
     *            the new deconstructAction
     */
    public void setDeconstructAction(DeconstructAction deconstructAction) {
        this.deconstructAction = deconstructAction;
    }

    /**
     * Sets the clearPrefsAction for this instance.
     *
     * @param clearPrefsAction
     *            the new clearPrefsAction
     */
    public void setClearPrefsAction(ConfirmClearPrefsAction clearPrefsAction) {
        this.clearPrefsAction = clearPrefsAction;
    }

    /**
     * Sets the settingsAction for this instance.
     *
     * @param settingsAction
     *            the new settingsAction
     */
    public void setSettingsAction(ConstructUtilityAction settingsAction) {
        this.settingsAction = settingsAction;
    }

    /** Starts the UI. */
    public void startUI() {
        showMainWindow();
        if (!prefs.wasInitialized()) {
            showSettingsWindow();
        }
    }

    /** Builds and shows the main window. */
    public void showMainWindow() {
        /* Create UI. */
        JPanel pane = new JPanel(new GridBagLayout());

        /* Arrange the components */
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeProjectActionButton("action.construct", "action-construct", ImageSize.W_16, constructAction), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeProjectActionButton("action.deconstruct", "action-deconstruct", ImageSize.W_16, deconstructAction),
                c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeActionButton(null, "icon-settings", ImageSize.W_32, settingsAction), c);

        Window window = makeWindow("app.name", "icon-timetracker", pane);
        window.addWindowListener(new WindowAdapter() {

            /*
             * (non-Javadoc)
             *
             * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event. WindowEvent)
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Frame[] windows = JFrame.getFrames();
                for (Frame w : windows) {
                    w.dispose();
                }
            }
        });
        window.setVisible(true);
    }

    /** Builds and shows the settings window. */
    public void showSettingsWindow() {
        /* Create UI. */
        JPanel pane = new JPanel(new GridBagLayout());

        /* Arrange the components */
        GridBagConstraints c;

        // project directory (text)
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new ProjectTextFieldChooser(location), c);

        // project directory (button)
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new ProjectButtonChooser(images, i18n, location), c);

        // Locale (icon)
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new JLabel(new ImageIcon(images.getImage("image-prefs-locale", ImageSize.W_16))), c);

        // Locale (combo)
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new LocaleComboBox(i18n), c);

        // Clear preferences
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeActionButton("action.prefs.clear", "action-clear-prefs", ImageSize.W_16, clearPrefsAction), c);

        makeWindow("app.settings", "icon-settings", pane).setVisible(true);
    }

    private Window makeWindow(String titleKey, String iconKey, Container content) {
        /* The basics. */
        JFrame window = new I14edJFrame(i18n, titleKey);
        window.setIconImages(images.getImages(iconKey));
        /* Make sure thread is ended when window is closed. */
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Add content and adapt size to fit the content. */
        window.setContentPane(content);
        window.pack();
        // Center on screen
        window.setLocationRelativeTo(null);

        return window;
    }

    private JButton makeIconButton(String textKey, String iconKey, ImageSize iconSize) {
        Image icon = images.getImage(iconKey, iconSize);
        return new I14edJButton(i18n, textKey, icon != null ? new ImageIcon(icon) : null);
    }

    private <T extends Action> JButton makeActionButton(String textKey, String iconKey, ImageSize iconSize, T action) {
        JButton btn = makeIconButton(textKey, iconKey, iconSize);
        btn.addActionListener(new ActionExecutor<>(action, i18n));
        return btn;
    }

    private JButton makeProjectActionButton(String textKey, String iconKey, ImageSize iconSize, ProjectAction action) {
        JButton btn = makeActionButton(textKey, iconKey, iconSize, action);
        location.registerForUpdate(new ProjectButtonListener(btn));
        return btn;
    }

    private static class ProjectButtonListener implements UpdateListener {
        private final JButton btn;

        private ProjectButtonListener(JButton btn) {
            this.btn = btn;
        }

        @Override
        public void notifyLocationUpdate(File location, boolean valid) {
            btn.setEnabled(valid);
        }
    }
}
