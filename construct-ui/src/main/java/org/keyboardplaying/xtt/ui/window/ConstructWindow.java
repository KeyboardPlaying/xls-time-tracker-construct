package org.keyboardplaying.xtt.ui.window;

import java.awt.Container;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;

import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Common parent for construct application windows
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public abstract class ConstructWindow extends JFrame {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 264294138687954787L;

    private I18nHelper i18nHelper;

    private String textKey;
    private String iconKey;

    {
        /* Make sure thread is ended on close. */
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Sets the i18nHelper for this instance.
     *
     * @param i18nHelper
     *            the new i18nHelper
     */
    @Autowired
    public void setI18nHelper(I18nHelper i18nHelper) {
        this.i18nHelper = i18nHelper;
    }

    protected I18nHelper getI18nHelper() {
        return i18nHelper;
    }

    /**
     * Sets the textKey for this instance.
     *
     * @param textKey
     *            the new textKey
     */
    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    /**
     * Sets the iconKey for this instance.
     *
     * @param iconKey
     *            the new iconKey
     */
    public void setIconKey(String iconKey) {
        this.iconKey = iconKey;
    }

    /** Initializes the component. */
    @PostConstruct
    public void init() {
        /* Title and icon. */
        this.setTitle(i18nHelper.getMessage(textKey));
        // TODO ImageLoader should be a singleton
        this.setIconImages(new ImageLoader().getImages(iconKey));

        /* Now the content. */
        this.setContentPane(buildContent());

        /* Adapt size to fit the content. */
        this.setResizable(false);
        this.pack();
        // Center on screen.
        this.setLocationRelativeTo(null);
    }

    protected abstract Container buildContent();
}
