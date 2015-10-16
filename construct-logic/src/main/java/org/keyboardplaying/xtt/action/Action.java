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

/**
 * An interface for actions that can be executed from the utility.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public interface Action {

    // FIXME make a specific Exception with localized message instead
    /**
     * Returns the message to be displayed if the action was not successfully performed.
     *
     * @return the message in case of execution failure
     */
    String getUnsuccessMessage();

    /**
     * Executes the action.
     *
     * @return {@code true} if the action was successfully performed, {@code false} otherwise
     * @throws Exception
     *             when the exception could not be performed
     */
    boolean perform() throws Exception;
}
