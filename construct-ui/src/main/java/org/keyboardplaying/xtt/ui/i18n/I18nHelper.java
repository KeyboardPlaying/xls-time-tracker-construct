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

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A helper for internationalization.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class I18nHelper {

    /**
     * The key the locale is stored under in the preferences.
     */
    // Use protected rather than default for package-private
    @SuppressWarnings("WeakerAccess")
    protected static final String LOCALE_PREFKEY = "locale";

    private static final Logger LOG = LoggerFactory.getLogger(I18nHelper.class);

    private static final String BUNDLE_BASE_NAME = "org.keyboardplaying.xtt.ui.i18n.Messages";
    private static final String LOCALE_REGEX = "([a-z]{2})(?:_([A-Z]{2})?(?:_([a-zA-Z]+))?(?:_#.+)?)?";

    private final Set<I14ed> i14ed = new HashSet<>();

    private PreferencesHelper preferences;

    private ResourceBundle bundle;

    /**
     * Creates a new instance and sets it the locale to its default.
     */
    public I18nHelper() {
        updateResourceBundle(Locale.getDefault());
    }

    /**
     * Initializes the internationalization helper with the saved locale.
     */
    @PostConstruct
    public void init() {
        String locale = preferences.get(LOCALE_PREFKEY);
        Locale l = locale == null ? Locale.getDefault() : parseLocale(locale);
        updateResourceBundle(l);
    }

    /**
     * Sets the preferences helper.
     *
     * @param preferences the preferences helper
     */
    @Autowired
    public void setPreferences(PreferencesHelper preferences) {
        this.preferences = preferences;
    }

    protected void updateResourceBundle(Locale locale) {
        this.bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    /**
     * Registers an {@link I14ed} component to be notified when the locale changes.
     *
     * @param internationalized the component to notify of locale changes
     */
    public void register(I14ed internationalized) {
        this.i14ed.add(internationalized);
    }

    /**
     * Parses the locale from the supplied representation.
     *
     * @param locale the string representation of a locale (e.g.: "en", "de_DE", "_GB", "en_US_WIN", "de__POSIX", "fr_MAC")
     * @return the associated {@link Locale} or default locale if format is incorrect
     */
    public Locale parseLocale(String locale) {
        Matcher m = Pattern.compile(LOCALE_REGEX).matcher(locale);
        Locale l;

        if (!m.matches()) {
            LOG.warn(locale + " does not match the expected format for a locale");
            l = Locale.getDefault();
        } else {
            String language = m.group(1);
            String country = m.group(2);
            String variant = m.group(3);
            if (variant != null) {
                l = new Locale(language, country, variant);
            } else if (country != null) {
                l = new Locale(language, country);
            } else {
                l = new Locale(language);
            }
        }

        return l;
    }

    /**
     * Returns the current locale.
     *
     * @return the current locale
     */
    public Locale getLocale() {
        return bundle.getLocale();
    }

    /**
     * Sets the locale for the messages to display.
     *
     * @param locale the locale to use
     */
    public void setLocale(Locale locale) {
        updateResourceBundle(locale);
        preferences.set(LOCALE_PREFKEY, locale.toString());
        for (I14ed internationalized : i14ed) {
            internationalized.updateMessages(this);
        }
    }

    /**
     * Gets the string message corresponding to the key.
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getMessage(String key) {
        return bundle.getString(key);
    }

    /**
     * Returns a list of all locales a bundle has been written for.
     *
     * @return a list of all locales availables within the application
     */
    public List<Locale> getAvailableLocales() {

        /* Convert base name to a path. */
        String bundlePath = BUNDLE_BASE_NAME.replaceAll("\\.", "/") + "_%s.properties";

        List<Locale> available = new ArrayList<>();

        /* Loop over all locales to find those we do support. */
        // Get a list of all locales the JVM supports
        Locale[] locales = Locale.getAvailableLocales();
        // Loop over those to find available locales
        for (Locale locale : locales) {
            URL bundleUrl = ClassLoader.getSystemResource(String.format(bundlePath, locale.toString()));
            if (bundleUrl != null) {
                available.add(locale);
            }
        }

        /* Return the list. */
        return available;
    }
}
