package org.keyboardplaying.construct.action;

import org.keyboardplaying.construct.ConstructPaths;

/**
 * @author cyChop (http://keyboardplaying.org)
 */
public class DeconstructAction implements Action {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getLabel()
     */
    @Override
    public String getLabel() {
        return "Deconstruct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ConstructPaths.CONSTRUCT_PATH
                + " missing or the directory " + ConstructPaths.DECONSTRUCT_PATH + "?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() {
        // TODO Auto-generated method stub
        return false;
    }
}
