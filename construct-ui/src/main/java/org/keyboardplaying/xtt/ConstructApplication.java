package org.keyboardplaying.xtt;

import org.keyboardplaying.xtt.ui.UIConfiguration;
import org.keyboardplaying.xtt.ui.UIController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Launcher and main class for the utility application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConstructApplication {

    /**
     * Main method for the application.
     *
     * @param args
     *            unused arguments
     */
    public static void main(String... args) {
        @SuppressWarnings("resource") // not closing, needed for prototypes
        ApplicationContext ctx = new AnnotationConfigApplicationContext(UIConfiguration.class);
        UIController controller = ctx.getBean(UIController.class);
        controller.startUI();
    }
}
