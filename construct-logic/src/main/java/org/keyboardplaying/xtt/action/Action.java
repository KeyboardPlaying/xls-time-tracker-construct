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
