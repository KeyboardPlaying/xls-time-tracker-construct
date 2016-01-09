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
package org.keyboardplaying.xtt.ui.i18n.swing;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import org.keyboardplaying.xtt.ui.i18n.I14ed;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;

/**
 * A frame with an internationalized title, listening to changes of the locale.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class I14edJFrame extends JFrame implements I14ed {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 1769010658444161195L;

    private final String titleKey;

    /**
     * Creates a new {@link I14edJFrame}.
     *
     * @param i18n
     *            the internationalization helper
     * @param titleKey
     *            the key for the title of the window
     * @throws HeadlessException
     *             if {@link GraphicsEnvironment#isHeadless()} returns true
     */
    public I14edJFrame(I18nHelper i18n, String titleKey) throws HeadlessException {
        super(i18n.getMessage(titleKey));
        this.titleKey = titleKey;
        i18n.register(this);
    }

    /**
     * Creates a new {@link I14edJFrame}.
     *
     * @param i18n
     *            the internationalization helper
     * @param titleKey
     *            the key for the title of the window
     * @param gc
     *            the {@link GraphicsConfiguration} that is used to construct the new JFrame with; if {@code gc} is
     *            {@code null}, the system default {@link GraphicsConfiguration} is assumed
     */
    public I14edJFrame(I18nHelper i18n, String titleKey, GraphicsConfiguration gc) {
        super(i18n.getMessage(titleKey), gc);
        this.titleKey = titleKey;
        i18n.register(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.ui.i18n.I14ed#updateMessages(org.keyboardplaying.xtt.ui.i18n.I18nHelper)
     */
    @Override
    public void updateMessages(I18nHelper helper) {
        this.setTitle(helper.getMessage(titleKey));
    }
}
