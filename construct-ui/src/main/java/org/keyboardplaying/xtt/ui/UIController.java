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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.i18n.swing.I14edJButton;
import org.keyboardplaying.xtt.ui.i18n.swing.I14edJFrame;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.keyboardplaying.xtt.ui.icon.ImageSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class UIController {

    @Autowired
    private I18nHelper i18nHelper;
    @Autowired
    private ImageLoader imageLoader;

    @Autowired
    private PreferencesHelper preferencesHelper;
    @Autowired
    private ProjectLocationHelper locationHelper;

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
        this.i18nHelper = i18nHelper;
    }

    /**
     * Sets the imageLoader for this instance.
     *
     * @param imageLoader
     *            the new imageLoader
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    /**
     * Sets the preferencesHelper for this instance.
     *
     * @param preferencesHelper
     *            the new preferencesHelper
     */
    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    /**
     * Sets the locationHelper for this instance.
     *
     * @param locationHelper
     *            the new locationHelper
     */
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
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
        if (!preferencesHelper.wasInitialized()) {
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
        pane.add(makeProjectTextChooser(), c);

        // project directory (button)
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeProjectBtnChooser(), c);

        // Locale (icon)
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new JLabel(new ImageIcon(imageLoader.getImage("image-prefs-locale", ImageSize.W_16))), c);

        // Locale (combo)
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(new LocaleComboBox(i18nHelper), c);

        // Clear preferences
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeActionButton("action.prefs.clear", "action-clear-prefs", ImageSize.W_16, clearPrefsAction), c);

        makeWindow("app.settings", "icon-settings", pane).setVisible(true);
    }

    private ProjectTextFieldChooser makeProjectTextChooser() {
        return new ProjectTextFieldChooser(locationHelper);
    }

    private JButton makeProjectBtnChooser() {
        JButton btn = makeIconButton(null, "action-search-folder", ImageSize.W_16);
        btn.addActionListener(new ProjectDirectoryChooserListener(btn, makeDirectoryChooser(), locationHelper));
        return btn;
    }

    private JFileChooser makeDirectoryChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(locationHelper.getProjectLocation());
        chooser.setDialogTitle(i18nHelper.getMessage("project.directory.select"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        return chooser;
    }

    private Window makeWindow(String titleKey, String iconKey, Container content) {
        /* The basics. */
        JFrame window = new I14edJFrame(i18nHelper, titleKey);
        window.setIconImages(imageLoader.getImages(iconKey));
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
        Image icon = imageLoader.getImage(iconKey, iconSize);

        if (textKey == null) {
            return new JButton(new ImageIcon(icon));
        }

        return new I14edJButton(i18nHelper, textKey, icon != null ? new ImageIcon(icon) : null);
    }

    private <T extends Action> JButton makeActionButton(String textKey, String iconKey, ImageSize iconSize, T action) {
        JButton btn = makeIconButton(textKey, iconKey, iconSize);
        btn.addActionListener(new ActionExecutor<>(action, i18nHelper));
        return btn;
    }

    private JButton makeProjectActionButton(String textKey, String iconKey, ImageSize iconSize, ProjectAction action) {
        JButton btn = makeActionButton(textKey, iconKey, iconSize, action);
        locationHelper.registerForUpdate(new ProjectButtonListener(btn, locationHelper));
        return btn;
    }

    private static class ProjectDirectoryChooserListener implements ActionListener {

        private final JButton btn;
        private final JFileChooser chooser;

        private final ProjectLocationHelper locationHelper;

        public ProjectDirectoryChooserListener(JButton btn, JFileChooser chooser,
                ProjectLocationHelper locationHelper) {
            super();
            this.btn = btn;
            this.chooser = chooser;
            this.locationHelper = locationHelper;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (chooser.showOpenDialog(btn) == JFileChooser.APPROVE_OPTION) {
                locationHelper.setProjectLocation(chooser.getSelectedFile());
            }
        }
    }

    private static class ProjectButtonListener implements UpdateListener {
        private final JButton btn;
        private final ProjectLocationHelper locationHelper;

        private ProjectButtonListener(JButton btn, ProjectLocationHelper locationHelper) {
            this.btn = btn;
            this.locationHelper = locationHelper;
        }

        @Override
        public void notifyLocationUpdate() {
            btn.setEnabled(locationHelper.isValid());
        }
    }

    private static class ActionExecutor<T extends Action> implements ActionListener {

        private static final Logger LOG = LoggerFactory.getLogger(ActionExecutor.class);

        private final T action;
        private final I18nHelper i18n;

        public ActionExecutor(T action, I18nHelper i18n) {
            this.action = action;
            this.i18n = i18n;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                action.perform();
            } catch (ActionException ex) {
                displayActionError(i18n.getMessage(ex.getMessageKey()), ex.getCause());
                LOG.error(ex.getMessage(), ex);
            } catch (RuntimeException ex) {
                displayActionError("An unexpected error happened.", ex);
                LOG.error(ex.getMessage(), ex);
            }
        }

        private void displayActionError(String message, Throwable cause) {
            StringBuilder msg = new StringBuilder(message);
            if (cause != null) {
                msg.append('\n').append('\n').append(cause.getClass().getName());
                String causeMsg = cause.getMessage();
                if (causeMsg != null && causeMsg.trim().length() != 0) {
                    msg.append('\n').append('\n').append(causeMsg);
                }
                JOptionPane.showMessageDialog(null, msg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, msg.toString(), "Failure", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
