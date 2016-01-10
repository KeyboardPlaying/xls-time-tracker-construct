/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.xtt.ui.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;

/**
 * Test class for {@link I18nHelper}.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class I18nHelperTest {

    private static final String APP_NAME_KEY = "app.name";
    private static final String APP_NAME_EN = "XTT Construct";
    private static final String APP_NAME_FR = "Constructeur XTT";

    private I18nHelper helper;
    private PreferencesHelper prefs;

    /** Initializes the helper with English as default locale. */
    @Before
    public void init() {
        helper = new I18nHelper();
        prefs = mock(PreferencesHelper.class);
        when(prefs.get(I18nHelper.LOCALE_PREFKEY)).thenReturn(Locale.ENGLISH.toString());
        helper.setPrefs(prefs);
        helper.init();
    }

    /** Tests the initialization when no preferred locale is stored. */
    @Test
    public void testDefaultLocale() {
        // Prepare
        I18nHelper i18n = new I18nHelper();
        when(prefs.get(I18nHelper.LOCALE_PREFKEY)).thenReturn(null);
        i18n.setPrefs(prefs);

        // Execute
        I18nHelper spy = spy(i18n);
        spy.init();

        // Assert
        verify(spy).updateResourceBundle(Locale.getDefault());
    }

    /**
     * Tests {@link I18nHelper#setLocale(Locale)}, {@link I18nHelper#getLocale()} and
     * {@link I18nHelper#getMessage(String)}.
     */
    @Test
    public void testSetLocaleAndGetMessage() {
        // test default/English locale
        assertEquals(APP_NAME_EN, helper.getMessage(APP_NAME_KEY));
        assertEquals(Locale.ENGLISH, helper.getLocale());

        // test another locale
        helper.setLocale(Locale.FRENCH);
        verify(prefs).set(I18nHelper.LOCALE_PREFKEY, Locale.FRENCH.toString());
        assertEquals(APP_NAME_FR, helper.getMessage(APP_NAME_KEY));
        assertEquals(Locale.FRENCH, helper.getLocale());

        // test unavailable locale
        helper.setLocale(Locale.GERMAN);
        assertEquals(APP_NAME_EN, helper.getMessage(APP_NAME_KEY));
        assertEquals(Locale.ENGLISH, helper.getLocale());
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

    /** Tests {@link I18nHelper#parseLocale(String)}. */
    @Test
    public void testParseLocale() {
        for (Locale locale : Locale.getAvailableLocales()) {
            testLocale(locale);
        }

        // an invalid locale
        assertEquals(Locale.getDefault(), helper.parseLocale("this_IS_NOT_a#locale"));
    }

    private void testLocale(Locale locale) {
        Locale parsed = helper.parseLocale(locale.toString());
        String errorIndicator = "Tested: <" + locale.toString() + "> - ";
        assertEquals(errorIndicator, locale.getLanguage(), parsed.getLanguage());
        assertEquals(errorIndicator, locale.getCountry(), parsed.getCountry());
        assertEquals(errorIndicator, locale.getVariant(), parsed.getVariant());
        // Locales are not equal because script is not taken into account
        // assertEquals(errorIndicator, locale, parsed);
    }

    /** Tests the notification to internationalized components. */
    @Test
    public void testI14edNotification() {
        // Prepare
        I14edAppName i14ed = new I14edAppName();
        helper.register(i14ed);

        // Execute and assert
        assertEquals(APP_NAME_EN, i14ed.appName);

        helper.setLocale(Locale.FRENCH);
        assertEquals(APP_NAME_FR, i14ed.appName);
    }

    private final class I14edAppName implements I14ed {
        private String appName = helper.getMessage(APP_NAME_KEY);

        @Override
        public void updateMessages(I18nHelper i18n) {
            appName = i18n.getMessage(APP_NAME_KEY);
        }
    }
}
