package org.keyboardplaying.construct.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.keyboardplaying.construct.model.Project;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ProjectChooser extends JPanel {

    private JButton button;
    private JTextField textField;

    private Project project;

    public ProjectChooser(Project project) {
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

    public Project getProject() {
        return this.project;
    }

    public void setDirectory(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException();
        }

        this.project.setLocation(directory);
        updateTextField(directory);
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
                setDirectory(chooser.getSelectedFile());
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
            setDirectory(new File(path));
        }
    }
}
