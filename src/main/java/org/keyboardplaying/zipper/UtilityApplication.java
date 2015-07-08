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
package org.keyboardplaying.zipper;

import org.keyboardplaying.zipper.action.ConstructAction;
import org.keyboardplaying.zipper.action.DeconstructAction;
import org.keyboardplaying.zipper.ui.ApplicationWindowBuilder;

/**
 * Launcher and main class for the utility application.
 *
 * @author cyChop (http://keyboardplaying.org/)
 */
public class UtilityApplication {

    /**
     * Main method for the application.
     *
     * @param args
     *            optional arguments
     */
    public static void main(String... args) {
        new UtilityApplication().start();
    }

    /** Applies the UI preferences and starts the application. */
    public void start() {
        // Build and show window
        new ApplicationWindowBuilder().setTitle("xls-time-tracker construct")
                .addActions(new ConstructAction(), new DeconstructAction()).build()
                .setVisible(true);
    }
}
