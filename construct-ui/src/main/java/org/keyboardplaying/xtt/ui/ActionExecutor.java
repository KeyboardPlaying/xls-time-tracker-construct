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
package org.keyboardplaying.xtt.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.keyboardplaying.xtt.action.Action;
import org.keyboardplaying.xtt.action.ActionException;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link ActionListener} that executes an {@link Action}.
 *
 * @param <T> the type of action to execute
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ActionExecutor<T extends Action> implements ActionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ActionExecutor.class);

    private final T action;
    private final I18nHelper i18n;

    /**
     * Creates a new instance.
     *
     * @param action the action to execute
     * @param i18n   the internationalization helper
     */
    public ActionExecutor(T action, I18nHelper i18n) {
        this.action = action;
        this.i18n = i18n;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            action.perform();
        } catch (ActionException ex) {
            displayActionError(i18n.getMessage(ex.getMessageKey()), ex.getCause());
            LOG.error(ex.getMessage(), ex);
        } catch (RuntimeException ex) {
            displayActionError(i18n.getMessage("action.error.unexpected"), ex);
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void displayActionError(String message, Throwable cause) {
        StringBuilder msg = new StringBuilder(message);
        if (cause != null) {
            msg.append('\n').append('\n').append(cause.getClass().getName());
            String causeMsg = cause.getMessage();
            if (causeMsg != null && causeMsg.trim().length() != 0) {
                msg.append('\n').append('\n').append(causeMsg);
            }
            JOptionPane.showMessageDialog(null, msg.toString(), i18n.getMessage("action.error.title.error"),
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, msg.toString(), i18n.getMessage("action.error.title.failure"),
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
