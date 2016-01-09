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
package org.keyboardplaying.xtt.ui.components;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.keyboardplaying.xtt.ui.i18n.I18nHelper;

/**
 * A combo box to choose the preferred locale amongst the available ones.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class LocaleComboBox extends JComboBox<Locale> implements ItemListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -1168140715882439602L;

    private I18nHelper i18n;

    /**
     * Creates a new instance.
     *
     * @param i18n
     *            the internationalization helper
     */
    public LocaleComboBox(I18nHelper i18n) {
        super(makeStoreFromList(i18n.getAvailableLocales()));
        setSelectedItem(i18n.getLocale());
        setRenderer(new LocaleComboBoxRenderer());
        this.i18n = i18n;
        this.addItemListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            i18n.setLocale((Locale) event.getItem());
        }
    }

    /**
     * Converts a list of locales into a sorted array for display.
     *
     * @param locales
     *            the list of locales to display
     * @return the store of the combo box
     */
    private static Locale[] makeStoreFromList(List<Locale> locales) {
        List<Locale> clone = new ArrayList<>(locales);
        Collections.sort(locales, new Comparator<Locale>() {

            @Override
            public int compare(Locale o1, Locale o2) {
                return getDisplayText(o1).compareTo(getDisplayText(o2));
            }
        });

        return clone.toArray(new Locale[clone.size()]);
    }

    /**
     * Returns the display name of the locale in that same locale, with an upper-case first character.
     *
     * @param locale
     *            the locale to display
     * @return the localized display name of the locale
     *
     * @see Locale#getDisplayName()
     */
    private static String getDisplayText(Locale locale) {
        return locale.getDisplayName();
    }

    /**
     * A renderer to display locales using {@link LocaleComboBox#getDisplayText(Locale)}.
     *
     * @author Cyrille Chopelet (https://keyboardplaying.org)
     */
    private static class LocaleComboBoxRenderer extends JLabel implements ListCellRenderer<Locale>, Serializable {

        /** Generated serial version UID. */
        private static final long serialVersionUID = -2867829357496336327L;

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int,
         * boolean, boolean)
         */
        @Override
        public Component getListCellRendererComponent(JList<? extends Locale> list, Locale value, int index,
                boolean isSelected, boolean cellHasFocus) {
            setText(value.getDisplayName());
            return this;
        }
    }
}
