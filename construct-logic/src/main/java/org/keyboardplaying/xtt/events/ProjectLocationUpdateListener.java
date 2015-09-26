package org.keyboardplaying.xtt.events;

import org.keyboardplaying.xtt.configuration.ProjectLocation;

/**
 * An interface for listening to project configuration updates.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
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
