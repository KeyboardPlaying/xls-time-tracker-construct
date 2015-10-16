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

import org.keyboardplaying.xtt.ConstructConfiguration;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The configuration of the user interface for the construct application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
@Configuration
@Import(ConstructConfiguration.class)
@SuppressWarnings("javadoc")
public class UIConfiguration {

    @Bean
    public I18nHelper i18nHelper() {
        return new I18nHelper();
    }

    @Bean
    public ImageLoader imageLoader() {
        return new ImageLoader();
    }

    @Bean
    public ConfirmClearPrefsAction clearPrefsAction() {
        return new ConfirmClearPrefsAction();
    }

    @Bean
    public UIController uiController() {
        return new UIController();
    }
}
