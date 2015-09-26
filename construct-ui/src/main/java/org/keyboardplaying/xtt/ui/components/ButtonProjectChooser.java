package org.keyboardplaying.xtt.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.keyboardplaying.xtt.configuration.ProjectLocation;
import org.keyboardplaying.xtt.events.ProjectLocationUpdateListener;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
// TODO Javadoc
public class ButtonProjectChooser extends IconButton implements ActionListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 5844064194538203681L;

    private ProjectLocation location;

    private List<ProjectLocationUpdateListener> listeners = new ArrayList<>();

    /**
     * Creates a new instance.
     *
     * @param location
     *            the project location.
     */
    public ButtonProjectChooser(ProjectLocation location) {
        super("action-search-folder");

        this.location = location;

        addActionListener(this);
    }

    public void addProjectSettingUpdateListener(ProjectLocationUpdateListener listener) {
        this.listeners.add(listener);
    }

    public void removeProjectSettingUpdateListener(ProjectLocationUpdateListener listener) {
        this.listeners.remove(listener);
    }

    private void projectSettingUpdated() {
        for (ProjectLocationUpdateListener listener : listeners) {
            listener.projectLocationUpdated(location);
        }
    }

    private void setDirectory(File directory) {
        this.location.setRoot(directory);
        projectSettingUpdated();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(location.getRoot());
        chooser.setDialogTitle("Set xls-time-tracker location directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(ButtonProjectChooser.this) == JFileChooser.APPROVE_OPTION) {
            setDirectory(chooser.getSelectedFile());
        }
    }
}