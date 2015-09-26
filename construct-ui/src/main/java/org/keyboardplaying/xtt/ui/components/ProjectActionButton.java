package org.keyboardplaying.xtt.ui.components;

import org.keyboardplaying.xtt.action.ProjectAction;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener;

/**
 * A component to add a {@link ProjectAction} to a button.
 * <p/>
 * This buttons listens to the project location and is disabled if the location is invalid.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ProjectActionButton extends ActionButton<ProjectAction>implements UpdateListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 2131670110737366963L;

    private ProjectLocationHelper locationHelper;

    private boolean valid = true;

    /**
     * Sets the project location helper for this instance.
     *
     * @param locationHelper
     *            the new locationHelper
     */
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.keyboardplaying.xtt.ui.components.ActionButton#init()
     */
    @Override
    public void init() {
        super.init();
        this.locationHelper.registerForUpdate(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener#notifyLocation()
     */
    @Override
    public void notifyLocationUpdate() {
        if (valid != locationHelper.isValid()) {
            valid = !valid;
            setEnabled(valid);
        }
    }
}
