package org.keyboardplaying.xtt.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;

/**
 * A tool to choose the directory the file will execute in.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ProjectButtonChooser extends IconButton implements ActionListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 6416501114764961739L;

    private ProjectLocationHelper locationHelper;

    /**
     * Sets the project location helper for this instance.
     *
     * @param locationHelper
     *            the new project location helper
     */
    // @Autowired
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.ui.components.IconButton#init()
     */
    @Override
    public void init() {
        super.init();
        addActionListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(locationHelper.getProjectLocation());
        chooser.setDialogTitle(getMessage("project.directory.select"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(ProjectButtonChooser.this) == JFileChooser.APPROVE_OPTION) {
            this.locationHelper.setProjectLocation(chooser.getSelectedFile());
        }
    }
}
