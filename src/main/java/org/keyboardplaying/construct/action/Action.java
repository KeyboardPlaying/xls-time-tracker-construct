package org.keyboardplaying.construct.action;

/**
 * An interface for actions that can be executed from the utility.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public interface Action {

    /**
     * Returns the label this action should be displayed as.
     *
     * @return the label for this action
     */
    String getLabel();

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
     */
    boolean perform();
}
