package org.keyboardplaying.construct.action;

/**
 * An interface for actions that can be executed from the utility.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public interface Action {

    /*
     * In a perfect world, this should contain only the perform operations. Everything else is UI-related should be in a
     * UI wrapper. But, hey! it's only a minimalist JAR to zip and unzip files...
     */

    /**
     * Returns the key for the label this action should be displayed as.
     *
     * @return the label key
     */
    String getLabelKey();

    /**
     * Returns the name of the image representing this action.
     *
     * @return the name of the icon for this action
     */
    String getIconImageName();

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
    boolean perform() throws Exception;
}
