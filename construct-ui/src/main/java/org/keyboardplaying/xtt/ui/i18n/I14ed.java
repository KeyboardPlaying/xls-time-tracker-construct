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

/**
 * An interface for internationalized components.
 * <p/>
 * By implementing this interface and registering to the {@link I18nHelper}, the component can be updated if the locale
 * changes.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public interface I14ed {

    /**
     * This method will be called on components implementing this interface and registered to the {@link I18nHelper}
     * when the locale is changed.
     *
     * @param helper
     *            the updated helper so that listeners do not have to store it
     */
    void updateMessages(I18nHelper helper);
}
