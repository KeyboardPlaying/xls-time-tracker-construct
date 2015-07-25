package org.keyboardplaying.construct.events;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;

/**
 * An interface for listening to project configuration updates.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public interface ProjectConfigurationUpdateListener {

    /**
     * Action to perform when the configuration is updated.
     *
     * @param updated
     *            the updated configuration
     */
    void projectConfigurationUpdated(ProjectConfiguration updated);
}
