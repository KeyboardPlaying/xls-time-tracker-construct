package org.keyboardplaying.construct.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.keyboardplaying.construct.action.ClearPrefsAction;
import org.keyboardplaying.construct.action.ConstructAction;
import org.keyboardplaying.construct.action.DeconstructAction;
import org.keyboardplaying.construct.configuration.ProjectConfiguration;
import org.keyboardplaying.construct.events.ProjectConfigurationUpdateListener;
import org.keyboardplaying.construct.ui.components.ActionButton;
import org.keyboardplaying.construct.ui.components.ButtonProjectChooser;
import org.keyboardplaying.construct.ui.components.TextFieldProjectChooser;

/**
 * A utility class for building a window.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ConstructWindow extends JFrame implements ProjectConfigurationUpdateListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -994440020143809205L;

    private static final String TITLE = "xls-time-tracker construct";
    private static final String PRF_PROJECT_DIR = "project.dir";

    private List<JComponent> constructActions = new ArrayList<>();
    private boolean valid = true;

    private Preferences preferences;

    // Apply System look and feel the first time this class is loaded
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            // this is no custom look and feel and should not happen
        } catch (InstantiationException e) {
            // this is no custom look and feel and should not happen
        } catch (IllegalAccessException e) {
            // this is no custom look and feel and should not happen
        } catch (UnsupportedLookAndFeelException e) {
            // this is no custom look and feel and should not happen
        }
    }

    public ConstructWindow(Preferences preferences) {
        /* Title and icon. */
        super(TITLE);

        this.preferences = preferences;

        /* Make sure thread is ended on close. */
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Center on screen. */
        this.setLocationRelativeTo(null);

        /* Now the content. */
        this.setContentPane(buildContent());

        /* Adapt size to fit the content. */
        this.setResizable(false);
        this.pack();
    }

    private JPanel buildContent() {
        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);

        ProjectConfiguration project = loadProject();

        TextFieldProjectChooser textChooser = new TextFieldProjectChooser(project);
        textChooser.addProjectSettingUpdateListener(this);

        ButtonProjectChooser btnChooser = new ButtonProjectChooser(project);
        btnChooser.addProjectSettingUpdateListener(this);
        btnChooser.addProjectSettingUpdateListener(textChooser);

        ActionButton constructAction = new ActionButton(new ConstructAction(project));
        ActionButton deconstructAction = new ActionButton(new DeconstructAction(project));
        ActionButton clearPrefsAction = new ActionButton(new ClearPrefsAction(preferences));

        constructActions.add(constructAction);
        constructActions.add(deconstructAction);

        layoutFrame(layout, textChooser, btnChooser, constructAction, deconstructAction,
                clearPrefsAction);

        valid = !project.isValid();
        projectConfigurationUpdated(project);

        return pane;
    }

    private void layoutFrame(GroupLayout layout, TextFieldProjectChooser textChooser,
            ButtonProjectChooser btnChooser, ActionButton constructAction,
            ActionButton deconstructAction, ActionButton clearPrefsAction) {

        /* Link sizes. */
        layout.linkSize(SwingConstants.VERTICAL, textChooser, btnChooser);
        layout.linkSize(constructAction, deconstructAction, clearPrefsAction);

        /* The main groups. */
        ParallelGroup horizontal = layout.createParallelGroup();
        SequentialGroup vertical = layout.createSequentialGroup();

        /* First line. */
        SequentialGroup sequential = layout.createSequentialGroup();
        ParallelGroup parallel = layout.createParallelGroup();

        add(sequential, parallel, textChooser);
        add(sequential, parallel, btnChooser);

        horizontal.addGroup(sequential);
        vertical.addGroup(parallel);

        /* Second line. */
        sequential = layout.createSequentialGroup();
        parallel = layout.createParallelGroup();

        add(sequential, parallel, constructAction);
        add(sequential, parallel, deconstructAction);
        sequential.addPreferredGap(ComponentPlacement.UNRELATED);
        add(sequential, parallel, clearPrefsAction);

        horizontal.addGroup(sequential);
        vertical.addGroup(parallel);

        /* Set layout. */
        layout.setHorizontalGroup(horizontal);
        layout.setVerticalGroup(vertical);
    }

    private void add(SequentialGroup seq, ParallelGroup par, JComponent component) {
        seq.addComponent(component);
        par.addComponent(component);
    }

    private ProjectConfiguration loadProject() {
        ProjectConfiguration project;

        String path = preferences.get(PRF_PROJECT_DIR, null);
        if (path == null) {
            project = new ProjectConfiguration();
            storeProject(project);
        } else {
            project = new ProjectConfiguration(path);
        }

        return project;
    }

    private void storeProject(ProjectConfiguration project) {
        preferences.put(PRF_PROJECT_DIR, project.getLocation().getAbsolutePath());
    }

    /**
     * Activates or deactivates the action buttons according to the selected project directory
     * validity.
     *
     * @param updated
     *            {@inheritDoc}
     */
    @Override
    public void projectConfigurationUpdated(ProjectConfiguration updated) {
        if (valid != updated.isValid()) {
            valid = !valid;
            for (JComponent action : constructActions) {
                action.setEnabled(valid);
            }
            if (valid) {
                storeProject(updated);
            }
        }
    }
}
