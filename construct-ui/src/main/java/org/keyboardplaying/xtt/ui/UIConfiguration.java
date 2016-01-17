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

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.keyboardplaying.xtt.ConstructConfiguration;
import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The configuration of the user interface for the construct application.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@Configuration
@Import(ConstructConfiguration.class)
@SuppressWarnings("javadoc")
public class UIConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(UIConfiguration.class);

    /* Apply the system look and feel. */
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // this is no custom look and feel
            LOG.warn("An error occurred while loading the system look and feel.", e);
        }
    }

    @Bean
    public I18nHelper i18nHelper() {
        return new I18nHelper();
    }

    @Bean
    public ImageLoader imageLoader() {
        return new ImageLoader();
    }

    @Bean
    public ConfirmClearPrefsAction confirmClearPrefsAction() {
        return new ConfirmClearPrefsAction();
    }

    @Bean
    public ClearPrefsAction clearPrefsAction() {
        return new ClearPrefsAction();
    }

    @Bean
    public UIController uiController() {
        return new UIController();
    }
}
