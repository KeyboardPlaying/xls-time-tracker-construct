package org.keyboardplaying.xtt.ui.window;

import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.action.ConfirmClearPrefsAction;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectButtonChooser;
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The window for configuring the construct.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class SettingsWindow extends ConstructWindow {

    private ProjectLocationHelper locationHelper;
    private ConfirmClearPrefsAction clearPrefsAction;

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
     * Sets the clearPrefsAction for this instance.
     *
     * @param clearPrefsAction
     *            the new clearPrefsAction
     */
    @Autowired
    public void setClearPrefsAction(ConfirmClearPrefsAction clearPrefsAction) {
        this.clearPrefsAction = clearPrefsAction;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.ui.window.ConstructWindow#buildContent()
     */
    @Override
    protected Container buildContent() {
        /* Create UI. */
        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);

        /* Arrange the components */
        arrangeLayout(layout);

        /* We're done! */
        return pane;
    }

    private void arrangeLayout(GroupLayout layout) {

        ProjectTextFieldChooser projectTextChooser = makeProjectTextChooser();
        ProjectButtonChooser projectBtnChooser = makeProjectBtnChooser();

        /* Link sizes. */
        layout.linkSize(SwingConstants.VERTICAL, projectTextChooser, projectBtnChooser);

        /* The main groups. */
        ParallelGroup horizontal = layout.createParallelGroup();
        SequentialGroup vertical = layout.createSequentialGroup();

        /* First line. */
        SequentialGroup sequential = layout.createSequentialGroup();
        ParallelGroup parallel = layout.createParallelGroup();

        addComponent(projectTextChooser, sequential, parallel);
        addComponent(projectBtnChooser, sequential, parallel);

        horizontal.addGroup(sequential);
        vertical.addGroup(parallel);

        /* Second line. */
        sequential = layout.createSequentialGroup();
        parallel = layout.createParallelGroup();

        addComponent(makeClearPrefsButton(), sequential, parallel);

        horizontal.addGroup(sequential);
        vertical.addGroup(parallel);

        /* Set layout. */
        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);
    }

    private void addComponent(JComponent component, SequentialGroup seq, ParallelGroup par) {
        seq.addComponent(component);
        par.addComponent(component);
    }

    private ProjectTextFieldChooser makeProjectTextChooser() {
        ProjectTextFieldChooser projectTextChooser = new ProjectTextFieldChooser();
        projectTextChooser.setLocationHelper(this.locationHelper);
        projectTextChooser.init();
        return projectTextChooser;
    }

    private ProjectButtonChooser makeProjectBtnChooser() {
        ProjectButtonChooser projectButtonChooser = new ProjectButtonChooser();
        projectButtonChooser.setIconKey("action-search-folder");
        projectButtonChooser.setI18nHelper(getI18nHelper());
        projectButtonChooser.setLocationHelper(this.locationHelper);
        projectButtonChooser.init();
        return projectButtonChooser;
    }

    private ActionButton<ConstructUtilityAction> makeClearPrefsButton() {
        ActionButton<ConstructUtilityAction> clearPrefsButton = new ActionButton<>();
        clearPrefsButton.setTextKey("action.prefs.clear");
        clearPrefsButton.setIconKey("action-clear-prefs");
        clearPrefsButton.setI18nHelper(getI18nHelper());
        clearPrefsButton.setAction(this.clearPrefsAction);
        clearPrefsButton.init();
        return clearPrefsButton;
    }
}
