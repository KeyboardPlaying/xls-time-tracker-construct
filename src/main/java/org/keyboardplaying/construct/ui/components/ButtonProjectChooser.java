package org.keyboardplaying.construct.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.keyboardplaying.construct.configuration.ProjectConfiguration;
import org.keyboardplaying.construct.events.ProjectConfigurationUpdateListener;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
// TODO Javadoc
public class ButtonProjectChooser extends IconButton implements ActionListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -8086818773016169377L;

    private ProjectConfiguration project;

    private List<ProjectConfigurationUpdateListener> listeners = new ArrayList<>();

    public ButtonProjectChooser(ProjectConfiguration project) {
        super("folder-saved-search");

        this.project = project;

        addActionListener(this);
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

    private void projectSettingUpdated() {
        for (ProjectConfigurationUpdateListener listener : listeners) {
            listener.projectConfigurationUpdated(getProject());
        }
    }

    private void setDirectory(File directory) {
        this.project.setLocation(directory);
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
        chooser.setCurrentDirectory(project.getLocation());
        chooser.setDialogTitle("Set xls-time-tracker project directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(ButtonProjectChooser.this) == JFileChooser.APPROVE_OPTION) {
            setDirectory(chooser.getSelectedFile());
        }
    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see javax.swing.JComponent#getMinimumSize()
    // */
    // @Override
    // public Dimension getMinimumSize() {
    // return recalculateSize(super.getMinimumSize());
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see javax.swing.JComponent#getMaximumSize()
    // */
    // @Override
    // public Dimension getMaximumSize() {
    // return recalculateSize(super.getMaximumSize());
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see javax.swing.JComponent#getPreferredSize()
    // */
    // @Override
    // public Dimension getPreferredSize() {
    // return recalculateSize(super.getPreferredSize());
    // }
    //
    // private Dimension recalculateSize(Dimension size) {
    // Insets insets = getInsets();
    // int width = size.height - insets.top - insets.bottom + insets.left + insets.right;
    // return new Dimension(width, size.height);
    // }
}
