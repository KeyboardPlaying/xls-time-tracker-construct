package org.keyboardplaying.construct.events;

import org.keyboardplaying.construct.configuration.ProjectLocation;

/**
 * An interface for listening to project configuration updates.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public interface ProjectLocationUpdateListener {

    /**
     * Action to perform when the configuration is updated.
     *
     * @param location
     *            the updated configuration
     */
    void projectLocationUpdated(ProjectLocation location);
}
