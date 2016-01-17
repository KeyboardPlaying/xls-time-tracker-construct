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
package org.keyboardplaying.xtt;

import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.xlsx.XlsxBuilder;
import org.keyboardplaying.xtt.xlsx.XlsxNormalizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * The configuration of the logic module of the construct application.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
@SuppressWarnings("javadoc")
@Configuration
@PropertySource("classpath:context.properties")
public class ConstructConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PreferencesHelper preferencesHelper() {
        return new PreferencesHelper();
    }

    @Bean
    public ProjectLocationHelper locationHelper() {
        return new ProjectLocationHelper();
    }

    @Bean
    public XlsxBuilder xlsxBuilder() {
        return new XlsxBuilder();
    }

    @Bean
    public XlsxNormalizer xlsxNormalizer() {
        return new XlsxNormalizer();
    }

    @Bean
    public ConstructAction constructAction() {
        return new ConstructAction();
    }

    @Bean
    public DeconstructAction deconstructAction() {
        return new DeconstructAction();
    }

    @Bean
    public ClearPrefsAction clearPrefsAction() {
        return new ClearPrefsAction();
    }
}
