package org.keyboardplaying.construct.ui.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;
import org.keyboardplaying.construct.events.ProjectConfigurationUpdateListener;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
// TODO Javadoc
public class TextFieldProjectChooser extends JTextField
        implements DocumentListener, ProjectConfigurationUpdateListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -3701119858258431753L;

    private ProjectConfiguration project;

    private List<ProjectConfigurationUpdateListener> listeners = new ArrayList<>();

    public TextFieldProjectChooser(ProjectConfiguration project) {
        super();

        this.project = project;

        updateTextField(project.getLocation());
        getDocument().addDocumentListener(this);
    }

    public ProjectConfiguration getProject() {
        return this.project;
    }

    public void addProjectSettingUpdateListener(ProjectConfigurationUpdateListener listener) {
        this.listeners.add(listener);
    }

    public void removeProjectSettingUpdateListener(ProjectConfigurationUpdateListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void projectConfigurationUpdated(ProjectConfiguration updated) {
        updateTextField(updated.getLocation());
    }

    private void projectSettingUpdated() {
        for (ProjectConfigurationUpdateListener listener : listeners) {
            listener.projectConfigurationUpdated(getProject());
        }
    }

    private void setDirectory(File directory, boolean updateText) {
        this.project.setLocation(directory);
        if (updateText) {
            updateTextField(directory);
        }
        projectSettingUpdated();
    }

    private void updateTextField(File location) {
        setText(location.getAbsolutePath());
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        update();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    private void update() {
        String path = getText();
        setDirectory(new File(path), false);
    }
}
