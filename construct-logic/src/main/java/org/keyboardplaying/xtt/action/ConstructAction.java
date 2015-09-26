package org.keyboardplaying.xtt.action;

import java.io.IOException;

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.zip.Zipper;

/**
 * Constructs an XLSX file from the exploded version.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConstructAction implements ProjectAction {

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
        return "The action could not be performed. Is the Excel file locked?";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.action.ProjectAction#perform()
     */
    @Override
    public boolean perform() throws IOException {
        return new Zipper(locationHelper.getDeconstructedDirectory(), locationHelper.getConstructedFile())
                .cleanAndBuildTarget();
    }
}
