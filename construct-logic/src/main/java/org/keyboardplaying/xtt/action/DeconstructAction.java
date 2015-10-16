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

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.zip.Unzipper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Updates the exploded version of the tracker based on the XLSX file.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DeconstructAction implements ProjectAction {

    private ProjectLocationHelper locationHelper;

    /**
     * Sets the project location helper for this instance.
     *
     * @param locationHelper
     *            the new project location helper
     */
    @Autowired
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file Excel file missing?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public boolean perform() throws IOException {
        return new Unzipper(locationHelper.getConstructedFile(), locationHelper.getDeconstructedDirectory())
                .cleanAndBuildTarget();
    }
}
