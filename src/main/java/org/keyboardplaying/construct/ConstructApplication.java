package org.keyboardplaying.construct;

import org.keyboardplaying.construct.action.ConstructAction;
import org.keyboardplaying.construct.action.DeconstructAction;
import org.keyboardplaying.construct.ui.WindowBuilder;

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
        // Build and show window
        new WindowBuilder().setTitle("xls-time-tracker construct")
                .addActions(new ConstructAction(), new DeconstructAction()).build()
                .setVisible(true);
    }
}
