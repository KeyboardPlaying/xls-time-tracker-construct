package org.keyboardplaying.construct.action;

import org.keyboardplaying.construct.ConstructPaths;

/**
 * @author cyChop (http://keyboardplaying.org)
 */
public class ConstructAction implements Action {

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getLabel()
     */
    @Override
    public String getLabel() {
        return "Construct";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ConstructPaths.CONSTRUCT_PATH
                + " locked?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() {
        throw new RuntimeException("This is a test exception");
        // TODO Auto-generated method stub
        // return false;
    }
}
