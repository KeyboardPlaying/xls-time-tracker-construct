package org.keyboardplaying.xtt.ui.window;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectActionButton;

/**
 * The main window for the construct application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class MainWindow extends ConstructWindow {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -7869895277316128051L;

    private ProjectActionButton constructButton;
    private ProjectActionButton deconstructButton;
    private ActionButton<ConstructUtilityAction> settingsButton;

    /**
     * Sets the constructButton for this instance.
     *
     * @param constructButton
     *            the new constructButton
     */
    public void setConstructButton(ProjectActionButton constructButton) {
        this.constructButton = constructButton;
    }

    /**
     * Sets the deconstructButton for this instance.
     *
     * @param deconstructButton
     *            the new deconstructButton
     */
    public void setDeconstructButton(ProjectActionButton deconstructButton) {
        this.deconstructButton = deconstructButton;
    }

    /**
     * Sets the settingsButton for this instance.
     *
     * @param settingsButton
     *            the new settingsButton
     */
    public void setSettingsButton(ActionButton<ConstructUtilityAction> settingsButton) {
        this.settingsButton = settingsButton;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.ui.window.ConstructWindow#buildContent()
     */
    @Override
    protected Container buildContent() {
        /* Create UI. */
        JPanel pane = new JPanel(new GridBagLayout());

        /* Arrange the components */
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(constructButton, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(deconstructButton, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(settingsButton, c);

        /* We're done! */
        return pane;
    }
}
