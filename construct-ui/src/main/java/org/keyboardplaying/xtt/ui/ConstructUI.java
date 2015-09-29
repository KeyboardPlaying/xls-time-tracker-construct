package org.keyboardplaying.xtt.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectButtonChooser;
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.IconSize;
import org.keyboardplaying.xtt.ui.window.MainWindow;
import org.keyboardplaying.xtt.ui.window.SettingsWindow;

/**
 * The application graphical context and main window.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConstructUI {

    private PreferencesHelper preferencesHelper;
    private ProjectLocationHelper locationHelper;

    private I18nHelper i18nHelper;

    private ConstructAction constructAction;
    private DeconstructAction deconstructAction;
    private ClearPrefsAction clearPrefsAction;

    /**
     * Sets the preferencesHelper for this instance.
     *
     * @param preferencesHelper
     *            the new preferencesHelper
     */
    // @Autowired
    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    /**
     * Sets the project location helper for this instance.
     *
     * @param locationHelper
     *            the new project location helper
     */
    // @Autowired
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /**
     * Sets the construct action for this instance.
     *
     * @param constructAction
     *            the new construct action
     */
    // @Autowired
    public void setConstructAction(ConstructAction constructAction) {
        this.constructAction = constructAction;
    }

    /**
     * Sets the deconstruct action for this instance.
     *
     * @param deconstructAction
     *            the new deconstruct action
     */
    // @Autowired
    public void setDeconstructAction(DeconstructAction deconstructAction) {
        this.deconstructAction = deconstructAction;
    }

    /**
     * Sets the preference cleaning action for this instance.
     *
     * @param clearPrefsAction
     *            the new preference cleaning action
     */
    // @Autowired
    public void setClearPrefsAction(ClearPrefsAction clearPrefsAction) {
        this.clearPrefsAction = clearPrefsAction;
    }

    /**
     * Configures the UI.
     * <p/>
     * In a more complete application, this would be performed using a Spring context, but I am trying to keep the
     * application as light as possible here.
     */
    public void configure() {
        this.i18nHelper = new I18nHelper();
    }

    /** Starts the UI of the application. */
    public void start() {
        applySystemLookAndFeel();

        makeMainWindow().setVisible(true);
        if (!preferencesHelper.wasInitialized()) {
            makeSettingsWindow().setVisible(true);
        }
    }

    private void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // this is no custom look and feel
            // therefore, no exception should happen
            e.printStackTrace();
        }
    }

    private MainWindow makeMainWindow() {
        ProjectActionButton constructButton = new ProjectActionButton();
        constructButton.setTextKey("action.construct");
        constructButton.setIconKey("action-construct");
        constructButton.setI18nHelper(this.i18nHelper);
        constructButton.setLocationHelper(this.locationHelper);
        constructButton.setAction(this.constructAction);
        constructButton.init();

        ProjectActionButton deconstructButton = new ProjectActionButton();
        deconstructButton.setTextKey("action.deconstruct");
        deconstructButton.setIconKey("action-deconstruct");
        deconstructButton.setI18nHelper(this.i18nHelper);
        deconstructButton.setLocationHelper(this.locationHelper);
        deconstructButton.setAction(this.deconstructAction);
        deconstructButton.init();

        ActionButton<ConstructUtilityAction> settingsButton = new ActionButton<>();
        settingsButton.setIconKey("icon-settings");
        settingsButton.setSize(IconSize._32);
        settingsButton.setAction(new ConstructUtilityAction() {

            @Override
            public boolean perform() throws Exception {
                return false;
            }

            @Override
            public String getUnsuccessMessage() {
                return "Some work left";
            }
        });
        settingsButton.init();

        MainWindow mainWindow = new MainWindow();
        mainWindow.setI18nHelper(i18nHelper);
        mainWindow.setTextKey("app.name");
        mainWindow.setIconKey("icon-timetracker");
        mainWindow.setConstructButton(constructButton);
        mainWindow.setDeconstructButton(deconstructButton);
        mainWindow.setSettingsButton(settingsButton);
        mainWindow.init();
        return mainWindow;
    }

    private SettingsWindow makeSettingsWindow() {
        ProjectTextFieldChooser projectTextChooser = new ProjectTextFieldChooser();
        projectTextChooser.setLocationHelper(this.locationHelper);
        projectTextChooser.init();

        ProjectButtonChooser projectButtonChooser = new ProjectButtonChooser();
        projectButtonChooser.setIconKey("action-search-folder");
        projectButtonChooser.setI18nHelper(this.i18nHelper);
        projectButtonChooser.setLocationHelper(this.locationHelper);
        projectButtonChooser.init();

        ActionButton<ConstructUtilityAction> clearPrefsButton = new ActionButton<>();
        clearPrefsButton.setTextKey("action.prefs.clear");
        clearPrefsButton.setIconKey("action-clear-prefs");
        clearPrefsButton.setI18nHelper(this.i18nHelper);
        clearPrefsButton.setAction(this.clearPrefsAction);
        clearPrefsButton.init();

        SettingsWindow settings = new SettingsWindow();
        settings.setI18nHelper(i18nHelper);
        settings.setTextKey("app.settings");
        settings.setIconKey("icon-settings");
        settings.setProjectTextChooser(projectTextChooser);
        settings.setProjectButtonChooser(projectButtonChooser);
        settings.setClearPrefsButton(clearPrefsButton);
        settings.init();
        return settings;
    }
}
