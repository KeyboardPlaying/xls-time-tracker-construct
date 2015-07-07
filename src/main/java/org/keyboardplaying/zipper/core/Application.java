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
package org.keyboardplaying.zipper.core;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class Application {

    /** The name for the main window. */
    public static final String WINDOW_NAME = "constructUtility";

    /**
     * Main method for the application.
     *
     * @param args
     *            optional arguments
     */
    public static void main(String... args) {
        new Application().start();
    }

    /** Applies the UI preferences and starts the application. */
    public void start() {
        /* Switch to native look & feel. */
        applySystemLookAndFeel();

        /* Run application */
        // Make main window.
        JFrame window = getMainWindow(getContentPane());
        // Window is ready, show it.
        window.setVisible(true);
    }

    private void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // this is no custom look and feel and should not happen
        } catch (InstantiationException e) {
            // this is no custom look and feel and should not happen
        } catch (IllegalAccessException e) {
            // this is no custom look and feel and should not happen
        } catch (UnsupportedLookAndFeelException e) {
            // this is no custom look and feel and should not happen
        }
    }

    public JPanel getContentPane() {
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));

        JButton btn = new JButton("Construct");
        pane.add(btn);

        btn = new JButton("Deconstruct");
        pane.add(btn);

        return pane;
    }

    public JFrame getMainWindow(JPanel contentPane) {

        /* Title and icon. */
        JFrame window = new JFrame("xls-time-tracker construct utility");

        /* Save window's name */
        window.setName(WINDOW_NAME);

        /* Make sure thread is ended on close. */
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* General styling */
        // setAlwaysOnTop(alwaysOnTop);
        window.setResizable(false);
        // center on screen
        window.setLocationRelativeTo(null);

        /* Now the content. */
        window.setContentPane(contentPane);

        /* Adapt size to fit the content. */
        window.pack();

        return window;
    }
}
