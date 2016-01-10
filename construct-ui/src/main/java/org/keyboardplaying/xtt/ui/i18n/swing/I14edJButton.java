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

import javax.swing.Icon;
import javax.swing.JButton;

import org.keyboardplaying.xtt.ui.i18n.I14ed;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;

/**
 * A button with an internationalized text, listening to changes of the locale.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class I14edJButton extends JButton implements I14ed {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -7311086663191166400L;

    private final String textKey;

    public I14edJButton(I18nHelper i18n, String textKey, Icon icon) {
        super(i18n.getMessage(textKey), icon);
        this.textKey = textKey;
        i18n.register(this);
    }

    public I14edJButton(I18nHelper i18n, String textKey) {
        super(i18n.getMessage(textKey));
        this.textKey = textKey;
        i18n.register(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.ui.i18n.I14ed#updateMessages(org.keyboardplaying.xtt.ui.i18n.I18nHelper)
     */
    @Override
    public void updateMessages(I18nHelper i18n) {
        this.setText(i18n.getMessage(textKey));
    }
}
