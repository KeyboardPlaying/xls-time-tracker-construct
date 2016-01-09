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

/**
 * A helper for internationalization.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class I18nHelper {

    private static final String BUNDLE_BASE_NAME = "org.keyboardplaying.xtt.ui.i18n.Messages";

    private Set<I14ed> i14ed = new HashSet<>();
    private ResourceBundle bundle;

    /** Creates a new instance and sets it the locale to its default. */
    public I18nHelper() {
        updateResourceBundle(Locale.getDefault());
    }

    private void updateResourceBundle(Locale locale) {
        this.bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
    }

    /**
     * Registers an {@link I14ed} component to be notified when the locale changes.
     *
     * @param internationalized
     *            the component to notify of locale changes
     */
    public void register(I14ed internationalized) {
        this.i14ed.add(internationalized);
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
     * @param locale
     *            the locale to use
     */
    public void setLocale(Locale locale) {
        updateResourceBundle(locale);
        for (I14ed internationalized : i14ed) {
            internationalized.updateMessages(this);
        }
    }

    /**
     * Gets the string message corresponding to the key.
     *
     * @param key
     *            the key for the desired string
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
