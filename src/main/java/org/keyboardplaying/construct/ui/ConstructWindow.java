package org.keyboardplaying.construct.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.keyboardplaying.construct.action.Action;
import org.keyboardplaying.construct.action.ActionWrapper;
import org.keyboardplaying.construct.action.ClearPrefsAction;
import org.keyboardplaying.construct.action.ConstructAction;
import org.keyboardplaying.construct.action.DeconstructAction;
import org.keyboardplaying.construct.events.ProjectSettingUpdateListener;
import org.keyboardplaying.construct.model.ProjectConfiguration;

/**
 * A utility class for building a window.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ConstructWindow extends JFrame implements ProjectSettingUpdateListener {

    private static final String TITLE = "xls-time-tracker construct";
    private static final String PRF_PROJECT_DIR = "project.dir";

    private List<JButton> actions = new ArrayList<>();
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

        /* General styling */
        // setAlwaysOnTop(alwaysOnTop);
        this.setResizable(false);
        // center on screen
        this.setLocationRelativeTo(null);

        /* Now the content. */
        this.setContentPane(buildContent());

        /* Adapt size to fit the content. */
        this.pack();
    }

    private JPanel buildContent() {
        ProjectConfiguration project = loadProject();

        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);

        ProjectChooser chooser = new ProjectChooser(project);
        chooser.addProjectSettingUpdateListener(this);

        SequentialGroup vGroup = layout.createSequentialGroup();
        ParallelGroup hGroup = layout.createParallelGroup();

        hGroup.addComponent(chooser);
        vGroup.addComponent(chooser);

        layout.setHorizontalGroup(hGroup);
        layout.setVerticalGroup(vGroup);

        buildActionButtons(layout, vGroup, hGroup, new ConstructAction(project),
                new DeconstructAction(project), new ClearPrefsAction(preferences));

        valid = !project.isValid();
        projectSettingUpdated(project);

        return pane;
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

    private void buildActionButtons(GroupLayout layout, SequentialGroup vGroup,
            ParallelGroup hGroup, Action... actions) {

        SequentialGroup seqGroup = layout.createSequentialGroup();
        ParallelGroup parGroup = layout.createParallelGroup();

        Component sizeRef = null;

        for (Action action : actions) {
            JButton btn = new JButton(action.getLabel());
            btn.addActionListener(new ActionWrapper(action));

            seqGroup.addComponent(btn);
            parGroup.addComponent(btn);
            this.actions.add(btn);

            if (sizeRef == null) {
                sizeRef = btn;
            } else {
                layout.linkSize(SwingConstants.HORIZONTAL, sizeRef, btn);
            }
        }

        // TODO adjust sizes of layout
        hGroup.addGroup(seqGroup);
        vGroup.addGroup(parGroup);
    }

    /**
     * Activates or deactivates the action buttons according to the selected project directory
     * validity.
     *
     * @param updated
     *            {@inheritDoc}
     */
    @Override
    public void projectSettingUpdated(ProjectConfiguration updated) {
        if (valid != updated.isValid()) {
            valid = !valid;
            for (JButton action : actions) {
                action.setEnabled(valid);
            }
            if (valid) {
                storeProject(updated);
            }
        }
    }
}
