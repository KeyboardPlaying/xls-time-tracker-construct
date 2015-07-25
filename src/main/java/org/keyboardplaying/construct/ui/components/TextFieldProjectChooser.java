package org.keyboardplaying.construct.ui.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.keyboardplaying.construct.configuration.ProjectLocation;
import org.keyboardplaying.construct.events.ProjectLocationUpdateListener;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
// TODO Javadoc
public class TextFieldProjectChooser extends JTextField
        implements DocumentListener, ProjectLocationUpdateListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 3202883511876422218L;

    private ProjectLocation location;

    private List<ProjectLocationUpdateListener> listeners = new ArrayList<>();

    public TextFieldProjectChooser(ProjectLocation location) {
        super();

        this.location = location;

        updateTextField(location.getRoot());
        getDocument().addDocumentListener(this);
    }

    public void addProjectSettingUpdateListener(ProjectLocationUpdateListener listener) {
        this.listeners.add(listener);
    }

    public void removeProjectSettingUpdateListener(ProjectLocationUpdateListener listener) {
        this.listeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.construct.events.ProjectLocationUpdateListener#
     * projectLocationUpdated(org.keyboardplaying.construct.configuration.ProjectLocation)
     */
    @Override
    public void projectLocationUpdated(ProjectLocation updated) {
        updateTextField(updated.getRoot());
    }

    private void projectSettingUpdated() {
        for (ProjectLocationUpdateListener listener : listeners) {
            listener.projectLocationUpdated(this.location);
        }
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
        location.setRoot(new File(path));
        projectSettingUpdated();
    }
}
