package org.keyboardplaying.xtt;

import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.ConstructUI;

/**
 * Launcher and main class for the utility application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConstructApplication {

    private PreferencesHelper preferencesHelper;
    private ProjectLocationHelper locationHelper;

    private ConstructAction constructAction;
    private DeconstructAction deconstructAction;
    private ClearPrefsAction clearPrefsAction;

    /**
     * Main method for the application.
     *
     * @param args
     *            unused arguments
     */
    public static void main(String... args) {
        ConstructApplication app = new ConstructApplication();
        app.configure();
        app.startUI();
    }

    /**
     * Configures the application.
     * <p/>
     * In a more complete application, this would be performed using a Spring context, but I am trying to keep the
     * application as light as possible here.
     */
    private void configure() {

        preferencesHelper = new PreferencesHelper();

        locationHelper = new ProjectLocationHelper();
        locationHelper.setPreferencesHelper(preferencesHelper);
        locationHelper.init();

        constructAction = new ConstructAction();
        constructAction.setLocationHelper(locationHelper);

        deconstructAction = new DeconstructAction();
        deconstructAction.setLocationHelper(locationHelper);

        clearPrefsAction = new ClearPrefsAction();
        clearPrefsAction.setPreferencesHelper(preferencesHelper);
    }

    private void startUI() {
        ConstructUI ui = new ConstructUI();

        ui.setPreferencesHelper(preferencesHelper);
        ui.setLocationHelper(locationHelper);
        ui.setConstructAction(constructAction);
        ui.setDeconstructAction(deconstructAction);
        ui.setClearPrefsAction(clearPrefsAction);

        // Ready? Go!
        ui.configure();
        ui.start();
    }
}
