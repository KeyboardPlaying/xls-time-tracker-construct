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
 * Exception for actions not performing correctly.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ActionException extends Exception {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 5196794290478647648L;

	private String userMessage;

	/**
	 * Constructs a new exception.
	 *
	 * @param userMessage
	 *            the message to be displayed to the user
	 */
	public ActionException(String userMessage) {
		this.userMessage = userMessage;
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message
	 * of {@code (cause==null ? null : cause.toString())} (which typically
	 * contains the class and detail message of cause). This constructor is
	 * useful for exceptions that are little more than wrappers for other
	 * throwables.
	 *
	 * @param userMessage
	 *            the message to be displayed to the user
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method)
	 */
	public ActionException(String userMessage, Throwable cause) {
		super(cause);
		this.userMessage = userMessage;
	}

	/**
	 * Returns the message to display to the user.
	 *
	 * @return the message to display to the user
	 */
	public String getUserMessage() {
		return userMessage;
	}
}
