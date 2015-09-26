package org.keyboardplaying.xtt.action;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.zip.Unzipper;

/**
 * Updates the exploded version of the tracker based on the XLSX file.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class DeconstructAction implements ProjectAction {

    private ProjectLocationHelper locationHelper;

    /**
     * Sets the project location helper for this instance.
     *
     * @param locationHelper
     *            the new project location helper
     */
    // @Autowired
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#getUnsuccessMessage()
     */
    @Override
    public String getUnsuccessMessage() {
        return "The action could not be performed. Is the file Excel file missing?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public boolean perform() throws FileNotFoundException, IOException {
        return new Unzipper(locationHelper.getConstructedFile(), locationHelper.getDeconstructedDirectory())
                .cleanAndBuildTarget();
    }
}
