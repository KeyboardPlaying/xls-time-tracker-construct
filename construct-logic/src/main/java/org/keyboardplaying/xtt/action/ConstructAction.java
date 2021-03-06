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

import java.io.IOException;

import org.keyboardplaying.file.Zipper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Constructs an XLSX file from the exploded version.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public class ConstructAction implements ProjectAction {

    private ProjectLocationHelper locationHelper;

    /**
     * Sets the project location helper for this instance.
     *
     * @param locationHelper the new project location helper
     */
    @Autowired
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public void perform() throws ActionException {
        try {
            Zipper.zip(locationHelper.getDeconstructedDirectory(), locationHelper.getConstructedFile(), true, false);
        } catch (IOException e) {
            throw new ActionException("action.error.construct", e);
        }
    }
}
