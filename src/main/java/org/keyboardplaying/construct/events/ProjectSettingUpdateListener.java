package org.keyboardplaying.construct.events;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;

/**
 * @author cyChop (http://keyboardplaying.org)
 */
public interface ProjectSettingUpdateListener {
    void projectSettingUpdated(ProjectConfiguration updated);
}
