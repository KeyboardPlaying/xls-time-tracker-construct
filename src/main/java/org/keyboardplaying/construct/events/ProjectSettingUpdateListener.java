package org.keyboardplaying.construct.events;

import org.keyboardplaying.construct.model.ProjectConfiguration;

/**
 * @author cyChop (http://keyboardplaying.org)
 */
public interface ProjectSettingUpdateListener {
    void projectSettingUpdated(ProjectConfiguration updated);
}
