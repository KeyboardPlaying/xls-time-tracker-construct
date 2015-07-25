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
import org.keyboardplaying.construct.configuration.ProjectLocation;
import org.keyboardplaying.construct.events.ProjectLocationUpdateListener;
import org.keyboardplaying.construct.ui.components.ActionButton;
import org.keyboardplaying.construct.ui.components.ButtonProjectChooser;
import org.keyboardplaying.construct.ui.components.TextFieldProjectChooser;
import org.keyboardplaying.construct.ui.i18n.MessageBundle;
import org.keyboardplaying.construct.ui.icon.ImageLoader;

/**
 * A utility class for building a window.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ConstructWindow extends JFrame implements ProjectLocationUpdateListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = 4689227798900897618L;

    private static final String TITLE = "app.name";
    private static final String ICON_NAME = "appointment-new";

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
        super(new MessageBundle().getMessage(TITLE));
        setIconImages(new ImageLoader().getImages(ICON_NAME));

        this.preferences = preferences;

        /* Make sure thread is ended on close. */
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Center on screen. */
        this.setLocationRelativeTo(null);

        /* Now the content. */
        this.setContentPane(buildContent(loadProject()));

        /* Adapt size to fit the content. */
        this.setResizable(false);
        this.pack();
    }

    private JPanel buildContent(ProjectLocation project) {
        /* Create UI. */
        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);

        /* Create components. */
        // Directory chooser
        TextFieldProjectChooser textChooser = new TextFieldProjectChooser(project);
        textChooser.addProjectSettingUpdateListener(this);

        ButtonProjectChooser btnChooser = new ButtonProjectChooser(project);
        btnChooser.addProjectSettingUpdateListener(this);
        btnChooser.addProjectSettingUpdateListener(textChooser);

        // Construct / deconstruct
        ActionButton constructAction = new ActionButton(new ConstructAction(project));
        ActionButton deconstructAction = new ActionButton(new DeconstructAction(project));
        constructActions.add(constructAction);
        constructActions.add(deconstructAction);

        // Clear prefs
        ActionButton clearPrefsAction = new ActionButton(new ClearPrefsAction(preferences));

        /* Arrange the components */
        arrangeLayout(layout, textChooser, btnChooser, constructAction, deconstructAction,
                clearPrefsAction);

        /* Initialize interface. */
        valid = !project.isValid();
        projectLocationUpdated(project);

        /* We're done! */
        return pane;
    }

    private void arrangeLayout(GroupLayout layout, TextFieldProjectChooser textChooser,
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

        addComponent(textChooser, sequential, parallel);
        addComponent(btnChooser, sequential, parallel);

        horizontal.addGroup(sequential);
        vertical.addGroup(parallel);

        /* Second line. */
        sequential = layout.createSequentialGroup();
        parallel = layout.createParallelGroup();

        addComponent(constructAction, sequential, parallel);
        addComponent(deconstructAction, sequential, parallel);
        sequential.addPreferredGap(ComponentPlacement.UNRELATED);
        addComponent(clearPrefsAction, sequential, parallel);

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

    /**
     * Loads the project location from the preferences or initialize it if nothing is set yet.
     *
     * @return the project location
     */
    private ProjectLocation loadProject() {
        ProjectLocation location;

        String path = preferences.get(PRF_PROJECT_DIR, null);
        if (path == null) {
            location = new ProjectLocation();
            storeProject(location);
        } else {
            location = new ProjectLocation(path);
        }

        return location;
    }

    /**
     * Stores the project location to the preferences.
     *
     * @param location
     *            the project location
     */
    private void storeProject(ProjectLocation location) {
        preferences.put(PRF_PROJECT_DIR, location.getRoot().getAbsolutePath());
    }

    /**
     * Activates or deactivates the action buttons according to the selected project directory
     * validity.
     *
     * @param updated
     *            {@inheritDoc}
     */
    @Override
    public void projectLocationUpdated(ProjectLocation updated) {
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
