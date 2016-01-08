package org.keyboardplaying.xtt.ui.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link I18nHelper}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class I18nHelperTest {

    private I18nHelper helper;

    /** Initializes the helper. */
    @Before
    public void init() {
        helper = new I18nHelper();
        helper.init();
    }

    /** Tests {@link I18nHelper#setLocale(Locale)} and {@link I18nHelper#getMessage(String)}. */
    @Test
    public void testSetLocaleAndGetMessage() {
        helper.setLocale(Locale.FRENCH);
        assertEquals("Constructeur XTT", helper.getMessage("app.name"));

        helper.setLocale(Locale.ENGLISH);
        assertEquals("XTT Construct", helper.getMessage("app.name"));

        // test unavailable locale
        helper.setLocale(Locale.GERMAN);
        assertEquals("XTT Construct", helper.getMessage("app.name"));
    }

    /** Tests {@link I18nHelper#getAvailableLocales()}. */
    @Test
    public void testGetAvailableLocales() {
        // Prepare
        List<Locale> available = Arrays.asList(Locale.ENGLISH, Locale.FRENCH);

        // Execute
        List<Locale> locales = helper.getAvailableLocales();

        // Assert
        assertEquals(available.size(), locales.size());
        assertTrue(available.containsAll(locales));
    }
}
