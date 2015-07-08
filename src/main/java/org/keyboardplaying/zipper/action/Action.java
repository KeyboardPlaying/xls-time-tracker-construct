package org.keyboardplaying.zipper.action;

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
     * Executes the action.
     *
     * @return {@code true} if the action was successfully performed, {@code false} otherwise
     */
    boolean perform();
}
