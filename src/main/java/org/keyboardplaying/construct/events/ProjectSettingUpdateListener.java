package org.keyboardplaying.construct.events;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;

/**
 * An interface for listening to project configuration updates.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public interface ProjectSettingUpdateListener {

    /**
     * Action to perform when the configuration is updated.
     *
     * @param updated
     *            the updated configuration
     */
    void projectSettingUpdated(ProjectConfiguration updated);
}
