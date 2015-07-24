package org.keyboardplaying.construct.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.keyboardplaying.construct.events.ProjectSettingUpdateListener;
import org.keyboardplaying.construct.model.ProjectConfiguration;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ProjectChooser extends JComponent {

    private JButton button;
    private JTextField textField;

    private ProjectConfiguration project;

    private List<ProjectSettingUpdateListener> listeners = new ArrayList<>();

    public ProjectChooser(ProjectConfiguration project) {
        this.project = project;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.button = new JButton("Find dir.");
        this.button.addActionListener(new ButtonActionListener());

        this.textField = new JTextField();
        updateTextField(project.getLocation());
        this.textField.getDocument().addDocumentListener(new TextFieldListener());

        this.add(textField);
        this.add(button);
    }

    public ProjectConfiguration getProject() {
        return this.project;
    }

    public void addProjectSettingUpdateListener(ProjectSettingUpdateListener listener) {
        this.listeners.add(listener);
    }

    public void removeProjectSettingUpdateListener(ProjectSettingUpdateListener listener) {
        this.listeners.remove(listener);
    }

    private void projectSettingUpdated() {
        for (ProjectSettingUpdateListener listener : listeners) {
            listener.projectSettingUpdated(getProject());
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
        this.textField.setText(location.getAbsolutePath());
    }

    private class ButtonActionListener implements ActionListener {
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

            if (chooser.showOpenDialog(ProjectChooser.this) == JFileChooser.APPROVE_OPTION) {
                setDirectory(chooser.getSelectedFile(), true);
            }
        }
    }

    private class TextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        private void update(DocumentEvent e) {
            String path = textField.getText();
            setDirectory(new File(path), false);
        }
    }
}