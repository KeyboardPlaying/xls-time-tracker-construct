package org.keyboardplaying.xtt.action;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.keyboardplaying.xtt.configuration.ProjectLocation;
import org.keyboardplaying.xtt.zip.Unzipper;

/**
 * Updates the exploded version of the tracker based on the XLSX file.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DeconstructAction implements Action {

    private ProjectLocation location;

    /**
     * Creates a new instance.
     *
     * @param location
     *            the location configuration
     */
    public DeconstructAction(ProjectLocation location) {
        this.location = location;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ProjectLocation.CONSTRUCT_PATH
                + " missing or the directory " + ProjectLocation.DECONSTRUCT_PATH + "?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.Action#perform()
     */
    @Override
    public boolean perform() throws FileNotFoundException, IOException {
        return new Unzipper(location.getConstructedFile(), location.getDeconstructedDirectory()).cleanAndBuildTarget();
    }
}
