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

    private static final String APP_NAME_KEY = "app.name";
    private static final String APP_NAME_EN = "XTT Construct";
    private static final String APP_NAME_FR = "Constructeur XTT";

    private I18nHelper helper;

    /** Initializes the helper with English as default locale. */
    @Before
    public void init() {
        Locale.setDefault(Locale.ENGLISH);
        helper = new I18nHelper();
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
