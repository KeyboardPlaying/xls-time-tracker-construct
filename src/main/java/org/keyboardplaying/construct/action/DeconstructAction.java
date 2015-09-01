package org.keyboardplaying.construct.action;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.keyboardplaying.construct.configuration.ProjectLocation;
import org.keyboardplaying.construct.file.Unzipper;

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
     * @see org.keyboardplaying.construct.action.Action#getLabelKey()
     */
    @Override
    public String getLabelKey() {
        return "action.deconstruct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getImageName()
     */
    @Override
    public String getIconImageName() {
        return "action-deconstruct";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file " + ProjectLocation.CONSTRUCT_PATH
                + " missing or the directory " + ProjectLocation.DECONSTRUCT_PATH + "?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.action.Action#perform()
     */
    @Override
    public boolean perform() throws FileNotFoundException, IOException {
        return new Unzipper(location.getConstructedFile(), location.getDeconstructedDirectory()).cleanAndBuildTarget();
    }
}
