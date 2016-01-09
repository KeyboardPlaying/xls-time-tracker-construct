package org.keyboardplaying.xtt.ui.i18n;

/**
 * An interface for internationalized components.
 * <p/>
 * By implementing this interface and registering to the {@link I18nHelper}, the component can be updated if the locale
 * changes.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public interface I14ed {

    /**
     * This method will be called on components implementing this interface and registered to the {@link I18nHelper}
     * when the locale is changed.
     */
    void updateMessages();
}
