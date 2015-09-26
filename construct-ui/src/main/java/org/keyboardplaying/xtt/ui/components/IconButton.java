package org.keyboardplaying.xtt.ui.components;

import java.awt.Image;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.IconSize;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;

/**
 * A component to easily make a button with an icon from an image embedded within the sources.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class IconButton extends JButton {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 8099387573563370857L;

    private static final IconSize IMG_SIZE = IconSize._16;

    private I18nHelper i18nHelper;

    private String textKey;
    private String iconKey;

    /**
     * Sets the i18nHelper for this instance.
     *
     * @param i18nHelper
     *            the new i18nHelper
     */
    // @Autowired
    public void setI18nHelper(I18nHelper i18nHelper) {
        this.i18nHelper = i18nHelper;
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

    /**
     * Gets the string message corresponding to the key.
     *
     * @param key
     *            the key for the desired string
     * @return the string for the given key
     */
    protected String getMessage(String key) {
        return i18nHelper.getMessage(key);
    }

    /** Initializes this instance. */
    // @PostConstruct
    public void init() {
        Objects.requireNonNull(iconKey, "An IconButton must be provided an image name.");

        String key = textKey;
        String text = key != null ? getMessage(key) : null;

        Image icon = new ImageLoader().getImage(iconKey, IMG_SIZE);

        super.init(text, new ImageIcon(icon));
    }
}
