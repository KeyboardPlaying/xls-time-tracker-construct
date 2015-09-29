package org.keyboardplaying.xtt.ui.action;

import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.ui.window.SettingsWindow;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * An action to open a settings window.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class OpenSettingsAction implements ConstructUtilityAction, ApplicationContextAware {

    private ApplicationContext ctx;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     * ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#perform()
     */
    @Override
    public boolean perform() throws Exception {
        ctx.getBean(SettingsWindow.class).setVisible(true);
        return true;
    }
}
