package org.keyboardplaying.xtt.ui.window;

import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectButtonChooser;
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;

/**
 * The window for configuring the construct.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class SettingsWindow extends ConstructWindow {

    private ProjectTextFieldChooser projectTextChooser;
    private ProjectButtonChooser projectButtonChooser;

    private ActionButton<ConstructUtilityAction> clearPrefsButton;

    /**
     * Sets the projectTextChooser for this instance.
     *
     * @param projectTextChooser
     *            the new projectTextChooser
     */
    public void setProjectTextChooser(ProjectTextFieldChooser projectTextChooser) {
        this.projectTextChooser = projectTextChooser;
    }

    /**
     * Sets the projectButtonChooser for this instance.
     *
     * @param projectButtonChooser
     *            the new projectButtonChooser
     */
    public void setProjectButtonChooser(ProjectButtonChooser projectButtonChooser) {
        this.projectButtonChooser = projectButtonChooser;
    }

    /**
     * Sets the clearPrefsButton for this instance.
     *
     * @param clearPrefsButton
     *            the new clearPrefsButton
     */
    public void setClearPrefsButton(ActionButton<ConstructUtilityAction> clearPrefsButton) {
        this.clearPrefsButton = clearPrefsButton;
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

        /* Link sizes. */
        layout.linkSize(SwingConstants.VERTICAL, projectTextChooser, projectButtonChooser);

        /* The main groups. */
        ParallelGroup horizontal = layout.createParallelGroup();
        SequentialGroup vertical = layout.createSequentialGroup();

        /* First line. */
        SequentialGroup sequential = layout.createSequentialGroup();
        ParallelGroup parallel = layout.createParallelGroup();

        addComponent(projectTextChooser, sequential, parallel);
        addComponent(projectButtonChooser, sequential, parallel);

        horizontal.addGroup(sequential);
        vertical.addGroup(parallel);

        /* Second line. */
        sequential = layout.createSequentialGroup();
        parallel = layout.createParallelGroup();

        addComponent(clearPrefsButton, sequential, parallel);

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
}
