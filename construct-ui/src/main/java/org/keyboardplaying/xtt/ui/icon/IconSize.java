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
package org.keyboardplaying.xtt.ui.icon;

/**
 * The available sizes for icons.
 *
 * @author Cyrille Chopelet (https://keyboardplaying.org)
 */
public enum IconSize {

    /** 16px wide. */
    W_16("16"),

    /** 22px wide. */
    W_22("22"),

    /** 32px wide. */
    W_32("32"),

    /** 64px wide. */
    W_64("64"),

    /** 128px wide. */
    W_128("128");

    private String path;

    private IconSize(String path) {
        this.path = path;
    }

    /**
     * Returns the name of the directory containing the icons for this size.
     *
     * @return the directory name
     */
    public String getPath() {
        return path;
    }
}
