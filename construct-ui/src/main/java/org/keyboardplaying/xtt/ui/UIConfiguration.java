package org.keyboardplaying.xtt.ui;

import org.keyboardplaying.xtt.ConstructConfiguration;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.action.OpenSettingsAction;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.window.MainWindow;
import org.keyboardplaying.xtt.ui.window.SettingsWindow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 * The configuration of the user interface for the construct application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Configuration
@Import(ConstructConfiguration.class)
@SuppressWarnings("javadoc")
public class UIConfiguration {

    @Bean
    public I18nHelper i18nHelper() {
        return new I18nHelper();
    }

    @Bean
    public OpenSettingsAction settingsAction() {
        return new OpenSettingsAction();
    }

    @Bean
    public ConfirmClearPrefsAction ClearPrefsAction() {
        return new ConfirmClearPrefsAction();
    }

    @Bean
    public MainWindow mainWindow() {
        MainWindow window = new MainWindow();
        window.setTextKey("app.name");
        window.setIconKey("icon-timetracker");
        return window;
    }

    @Bean
    @Scope("prototype")
    public SettingsWindow settings() {
        SettingsWindow window = new SettingsWindow();
        window.setTextKey("app.settings");
        window.setIconKey("icon-settings");
        return window;
    }
}
