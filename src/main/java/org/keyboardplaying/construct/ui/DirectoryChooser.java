package org.keyboardplaying.construct.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class DirectoryChooser extends JPanel {

    private JButton button;
    private JTextField textField;

    private File directory;

    public DirectoryChooser(File file) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.button = new JButton("Find dir.");
        this.button.addActionListener(new ButtonActionListener());

        this.textField = new JTextField();
        this.setDirectory(file);
        this.textField.setEditable(false);
        // TODO add change listener to edit path manually

        this.add(textField);
        this.add(button);
    }

    public File getSelectedDirectory() {
        return this.directory;
    }

    public void setDirectory(File file) {
        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException();
        }

        this.directory = file;
        this.textField.setText(file.getAbsolutePath());
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
            chooser.setCurrentDirectory(DirectoryChooser.this.getSelectedDirectory());
            chooser.setDialogTitle("Set xls-time-tracker project directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(DirectoryChooser.this) == JFileChooser.APPROVE_OPTION) {
                setDirectory(chooser.getSelectedFile());
            }
        }
    }
}
