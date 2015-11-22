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
package org.keyboardplaying.xtt.action;

import java.util.prefs.BackingStoreException;

import org.keyboardplaying.xtt.configuration.PreferencesHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An action to remove preferences from the computer the constructor is executed upon.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ClearPrefsAction implements ConstructUtilityAction {

    private PreferencesHelper preferencesHelper;

    /**
     * Sets the preferences helper for this instance.
     *
     * @param preferencesHelper
     *            the new preferences helper
     */
    @Autowired
    public void setPreferencesHelper(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public void perform() throws ActionException {
        try {
            preferencesHelper.clear();
        } catch (BackingStoreException e) {
            throw new ActionException("action.error.preferences.clear", e);
        }
    }
}
