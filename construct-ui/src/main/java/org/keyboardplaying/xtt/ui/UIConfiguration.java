package org.keyboardplaying.xtt.ui;

import org.keyboardplaying.xtt.ConstructConfiguration;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
    public ImageLoader imageLoader() {
        return new ImageLoader();
    }

    @Bean
    public ConfirmClearPrefsAction clearPrefsAction() {
        return new ConfirmClearPrefsAction();
    }

    @Bean
    public UIController uiController() {
        return new UIController();
    }
}
