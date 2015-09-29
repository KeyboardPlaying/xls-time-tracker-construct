package org.keyboardplaying.xtt;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The configuration of the logic module of the construct application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Configuration
@SuppressWarnings("javadoc")
public class ConstructConfiguration {

    /* Apply the system look and feel. */
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // this is no custom look and feel
            // therefore, no exception should happen
            e.printStackTrace();
        }
    }

    @Bean
    public PreferencesHelper preferencesHelper() {
        return new PreferencesHelper();
    }

    @Bean
    public ProjectLocationHelper locationHelper() {
        return new ProjectLocationHelper();
    }

    @Bean
    public ConstructAction constructAction() {
        return new ConstructAction();
    }

    @Bean
    public DeconstructAction deconstructAction() {
        return new DeconstructAction();
    }

    @Bean
    public ClearPrefsAction clearPrefsAction() {
        return new ClearPrefsAction();
    }
}
