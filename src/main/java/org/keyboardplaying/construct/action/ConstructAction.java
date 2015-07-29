package org.keyboardplaying.construct.action;

import java.io.IOException;

import org.keyboardplaying.construct.configuration.ProjectLocation;
import org.keyboardplaying.construct.file.Zipper;

/**
 * Constructs an XLSX file from the exploded version.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ConstructAction implements Action {

    private ProjectLocation location;

    /**
     * Creates a new instance.
     *
     * @param location
     *            the location configuration
     */
    public ConstructAction(ProjectLocation location) {
        this.location = location;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getLabelKey()
     */
    @Override
    public String getLabelKey() {
        return "action.construct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getImageName()
     */
    @Override
    public String getIconImageName() {
        return "action-construct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ProjectLocation.CONSTRUCT_PATH
                + " locked?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() throws IOException {
        return new Zipper(location.getDeconstructedDirectory(), location.getConstructedFile())
                .cleanAndBuildTarget();
    }
}
