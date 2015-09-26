package org.keyboardplaying.xtt.action;

import java.io.IOException;

import org.keyboardplaying.xtt.configuration.ProjectLocation;
import org.keyboardplaying.xtt.zip.Zipper;

/**
 * Constructs an XLSX file from the exploded version.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
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
     * @see org.keyboardplaying.xtt.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ProjectLocation.CONSTRUCT_PATH + " locked?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#perform()
     */
    @Override
    public boolean perform() throws IOException {
        return new Zipper(location.getDeconstructedDirectory(), location.getConstructedFile()).cleanAndBuildTarget();
    }
}
