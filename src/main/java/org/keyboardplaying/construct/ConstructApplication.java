package org.keyboardplaying.construct;

import java.util.prefs.Preferences;

import org.keyboardplaying.construct.ui.ConstructWindow;

/**
 * Launcher and main class for the utility application.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
public class ConstructApplication {

    /**
     * Main method for the application.
     *
     * @param args
     *            unused arguments
     */
    public static void main(String... args) {
        new ConstructApplication().start();
    }

    /** Applies the UI preferences and starts the application. */
    public void start() {
        // Load preferences manager
        Preferences preferences = Preferences.userRoot().node(getClass().getName());

        // Build and show window
        new ConstructWindow(preferences).setVisible(true);
    }
}
