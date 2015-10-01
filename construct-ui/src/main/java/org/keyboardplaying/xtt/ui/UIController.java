package org.keyboardplaying.xtt.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectButtonChooser;
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.IconSize;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cyrille Chopelet (http://keyboardplaying.org)
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
        public boolean perform() throws Exception {
            showSettingsWindow();
            return true;
        }

        @Override
        public String getUnsuccessMessage() {
            return "The configuration window could not be shown";
        }
    };

    public void startUI() {
        showMainWindow();
        if (!preferencesHelper.wasInitialized()) {
            showSettingsWindow();
        }
    }

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
        pane.add(makeConstructButton(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeDeconstructButton(), c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeSettingsButton(), c);

        makeWindow("app.name", "icon-timetracker", pane).setVisible(true);
    }

    private ProjectActionButton makeConstructButton() {
        ProjectActionButton constructButton = new ProjectActionButton();
        constructButton.setTextKey("action.construct");
        constructButton.setIconKey("action-construct");
        constructButton.setI18nHelper(i18nHelper);
        constructButton.setLocationHelper(locationHelper);
        constructButton.setAction(constructAction);
        constructButton.init();
        return constructButton;
    }

    private ProjectActionButton makeDeconstructButton() {
        ProjectActionButton deconstructButton = new ProjectActionButton();
        deconstructButton.setTextKey("action.deconstruct");
        deconstructButton.setIconKey("action-deconstruct");
        deconstructButton.setI18nHelper(i18nHelper);
        deconstructButton.setLocationHelper(locationHelper);
        deconstructButton.setAction(deconstructAction);
        deconstructButton.init();
        return deconstructButton;
    }

    private ActionButton<ConstructUtilityAction> makeSettingsButton() {
        ActionButton<ConstructUtilityAction> settingsButton = new ActionButton<>();
        settingsButton.setIconKey("icon-settings");
        settingsButton.setSize(IconSize._32);
        settingsButton.setAction(settingsAction);
        settingsButton.init();
        return settingsButton;
    }

    public void showSettingsWindow() {
        /* Create UI. */
        JPanel pane = new JPanel(new GridBagLayout());

        /* Arrange the components */
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeProjectTextChooser(), c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeProjectBtnChooser(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeClearPrefsButton(), c);

        makeWindow("app.name", "icon-timetracker", pane).setVisible(true);
    }

    private ProjectTextFieldChooser makeProjectTextChooser() {
        ProjectTextFieldChooser projectTextChooser = new ProjectTextFieldChooser();
        projectTextChooser.setLocationHelper(locationHelper);
        projectTextChooser.init();
        return projectTextChooser;
    }

    private ProjectButtonChooser makeProjectBtnChooser() {
        ProjectButtonChooser projectButtonChooser = new ProjectButtonChooser();
        projectButtonChooser.setIconKey("action-search-folder");
        projectButtonChooser.setI18nHelper(i18nHelper);
        projectButtonChooser.setLocationHelper(locationHelper);
        projectButtonChooser.init();
        return projectButtonChooser;
    }

    private ActionButton<ConstructUtilityAction> makeClearPrefsButton() {
        ActionButton<ConstructUtilityAction> clearPrefsButton = new ActionButton<>();
        clearPrefsButton.setTextKey("action.prefs.clear");
        clearPrefsButton.setIconKey("action-clear-prefs");
        clearPrefsButton.setI18nHelper(i18nHelper);
        clearPrefsButton.setAction(clearPrefsAction);
        clearPrefsButton.init();
        return clearPrefsButton;
    }

    private Window makeWindow(String titleKey, String iconKey, Container content) {
        /* The basics. */
        JFrame window = new JFrame(i18nHelper.getMessage(titleKey));
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
}
