package org.keyboardplaying.xtt.ui.components;

import java.io.File;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener;

/**
 * A text field representing the directory the construct will work in.
 * <p/>
 * The value can also be edited.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ProjectTextFieldChooser extends JTextField implements DocumentListener, UpdateListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -4443324929997149341L;

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

    /** Initializes this instance. */
    // @PostConstruct
    public void init() {
        updateTextField(locationHelper.getProjectLocation());
        getDocument().addDocumentListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.configuration.ProjectLocationHelper.UpdateListener#notifyLocation()
     */
    @Override
    public void notifyLocationUpdate() {
        updateTextField(locationHelper.getProjectLocation());
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        updateLocation();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        updateLocation();
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        updateLocation();
    }

    /** Updates the location of the project. This will trigger a "location updated event" */
    private void updateLocation() {
        locationHelper.setProjectLocation(getText());
    }

    /**
     * Updates the text field without firing the update event.
     *
     * @param directory
     *            the selected directory
     */
    private void updateTextField(File directory) {
        setText(directory.getAbsolutePath());
    }
}
