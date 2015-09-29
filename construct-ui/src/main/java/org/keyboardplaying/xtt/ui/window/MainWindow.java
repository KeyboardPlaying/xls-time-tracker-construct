package org.keyboardplaying.xtt.ui.window;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.action.OpenSettingsAction;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectActionButton;
import org.keyboardplaying.xtt.ui.icon.IconSize;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main window for the construct application.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class MainWindow extends ConstructWindow {

    /** Generated serial version UID. */

    private ProjectLocationHelper locationHelper;

    private ConstructAction constructAction;
    private DeconstructAction deconstructAction;
    private OpenSettingsAction settingsAction;

    /**
     * Sets the locationHelper for this instance.
     *
     * @param locationHelper
     *            the new locationHelper
     */
    @Autowired
    public void setLocationHelper(ProjectLocationHelper locationHelper) {
        this.locationHelper = locationHelper;
    }

    /**
     * Sets the constructAction for this instance.
     *
     * @param constructAction
     *            the new constructAction
     */
    @Autowired
    public void setConstructAction(ConstructAction constructAction) {
        this.constructAction = constructAction;
    }

    /**
     * Sets the deconstructAction for this instance.
     *
     * @param deconstructAction
     *            the new deconstructAction
     */
    @Autowired
    public void setDeconstructAction(DeconstructAction deconstructAction) {
        this.deconstructAction = deconstructAction;
    }

    /**
     * Sets the settingsAction for this instance.
     *
     * @param settingsAction
     *            the new settingsAction
     */
    @Autowired
    public void setSettingsAction(OpenSettingsAction settingsAction) {
        this.settingsAction = settingsAction;
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
        pane.add(makeConstructButton(), c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeDeconstructButton(), c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        pane.add(makeSettingsButton(), c);

        /* We're done! */
        return pane;
    }

    private ProjectActionButton makeConstructButton() {
        ProjectActionButton constructButton = new ProjectActionButton();
        constructButton.setTextKey("action.construct");
        constructButton.setIconKey("action-construct");
        constructButton.setI18nHelper(getI18nHelper());
        constructButton.setLocationHelper(this.locationHelper);
        constructButton.setAction(this.constructAction);
        constructButton.init();
        return constructButton;
    }

    private ProjectActionButton makeDeconstructButton() {
        ProjectActionButton deconstructButton = new ProjectActionButton();
        deconstructButton.setTextKey("action.deconstruct");
        deconstructButton.setIconKey("action-deconstruct");
        deconstructButton.setI18nHelper(getI18nHelper());
        deconstructButton.setLocationHelper(this.locationHelper);
        deconstructButton.setAction(this.deconstructAction);
        deconstructButton.init();
        return deconstructButton;
    }

    private ActionButton<ConstructUtilityAction> makeSettingsButton() {
        ActionButton<ConstructUtilityAction> settingsButton = new ActionButton<>();
        settingsButton.setIconKey("icon-settings");
        settingsButton.setSize(IconSize._32);
        settingsButton.setAction(settingsAction);
        settingsButton.init();
        return settingsButton;
    }
}
