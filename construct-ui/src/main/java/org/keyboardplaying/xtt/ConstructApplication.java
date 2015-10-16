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

import org.keyboardplaying.xtt.ui.UIConfiguration;
import org.keyboardplaying.xtt.ui.UIController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Launcher and main class for the utility application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConstructApplication {

    private ConstructApplication() {
    }

    /**
     * Main method for the application.
     *
     * @param args
     *            unused arguments
     */
    public static void main(String... args) {
        @SuppressWarnings("resource") // not closing, needed for prototypes
        ApplicationContext ctx = new AnnotationConfigApplicationContext(UIConfiguration.class);
        UIController controller = ctx.getBean(UIController.class);
        controller.startUI();
    }
}
