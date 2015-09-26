package org.keyboardplaying.xtt.ui;

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

import org.keyboardplaying.xtt.action.ClearPrefsAction;
import org.keyboardplaying.xtt.action.ConstructAction;
import org.keyboardplaying.xtt.action.ConstructUtilityAction;
import org.keyboardplaying.xtt.action.DeconstructAction;
import org.keyboardplaying.xtt.configuration.ProjectLocationHelper;
import org.keyboardplaying.xtt.ui.components.ActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectActionButton;
import org.keyboardplaying.xtt.ui.components.ProjectButtonChooser;
import org.keyboardplaying.xtt.ui.components.ProjectTextFieldChooser;
import org.keyboardplaying.xtt.ui.i18n.I18nHelper;
import org.keyboardplaying.xtt.ui.icon.ImageLoader;

/**
 * The application graphical context and main window.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ConstructWindow extends JFrame {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -4415822151746371919L;

    private ProjectLocationHelper locationHelper;

    private I18nHelper i18nHelper;

    private ConstructAction constructAction;
    private DeconstructAction deconstructAction;
    private ClearPrefsAction clearPrefsAction;

    private ProjectTextFieldChooser projectTextChooser;
    private ProjectButtonChooser projectButtonChooser;

    private ProjectActionButton constructButton;
    private ProjectActionButton deconstructButton;
    private ActionButton<ConstructUtilityAction> clearPrefsButton;

    static {
        /* Apply System look and feel the first time this class is loaded. */
        applySystemLookAndFeel();
    }

    {
        /* Make sure thread is ended on close. */
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static void applySystemLookAndFeel() {
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

    /**
     * Sets the construct action for this instance.
     *
     * @param constructAction
     *            the new construct action
     */
    // @Autowired
    public void setConstructAction(ConstructAction constructAction) {
        this.constructAction = constructAction;
    }

    /**
     * Sets the deconstruct action for this instance.
     *
     * @param deconstructAction
     *            the new deconstruct action
     */
    // @Autowired
    public void setDeconstructAction(DeconstructAction deconstructAction) {
        this.deconstructAction = deconstructAction;
    }

    /**
     * Sets the preference cleaning action for this instance.
     *
     * @param clearPrefsAction
     *            the new preference cleaning action
     */
    // @Autowired
    public void setClearPrefsAction(ClearPrefsAction clearPrefsAction) {
        this.clearPrefsAction = clearPrefsAction;
    }

    /**
     * Configures the UI.
     * <p/>
     * In a more complete application, this would be performed using a Spring context, but I am trying to keep the
     * application as light as possible here.
     */
    public void configure() {
        this.i18nHelper = new I18nHelper();

        this.projectTextChooser = new ProjectTextFieldChooser();
        this.projectTextChooser.setLocationHelper(this.locationHelper);
        this.projectTextChooser.init();

        this.projectButtonChooser = new ProjectButtonChooser();
        this.projectButtonChooser.setIconKey("action-search-folder");
        this.projectButtonChooser.setI18nHelper(this.i18nHelper);
        this.projectButtonChooser.setLocationHelper(this.locationHelper);
        this.projectButtonChooser.init();

        this.constructButton = new ProjectActionButton();
        this.constructButton.setTextKey("action.construct");
        this.constructButton.setIconKey("action-construct");
        this.constructButton.setI18nHelper(this.i18nHelper);
        this.constructButton.setLocationHelper(this.locationHelper);
        this.constructButton.setAction(this.constructAction);
        this.constructButton.init();

        this.deconstructButton = new ProjectActionButton();
        this.deconstructButton.setTextKey("action.deconstruct");
        this.deconstructButton.setIconKey("action-deconstruct");
        this.deconstructButton.setI18nHelper(this.i18nHelper);
        this.deconstructButton.setLocationHelper(this.locationHelper);
        this.deconstructButton.setAction(this.deconstructAction);
        this.deconstructButton.init();

        this.clearPrefsButton = new ActionButton<>();
        this.clearPrefsButton.setTextKey("action.prefs.clear");
        this.clearPrefsButton.setIconKey("action-clear-prefs");
        this.clearPrefsButton.setI18nHelper(this.i18nHelper);
        this.clearPrefsButton.setAction(this.clearPrefsAction);
        this.clearPrefsButton.init();
    }

    /** Initializes this instance. */
    public void init() {
        /* Title and icon. */
        setTitle(i18nHelper.getMessage("app.name"));
        setIconImages(new ImageLoader().getImages("icon-timetracker"));

        /* Now the content. */
        this.setContentPane(buildContent());

        /* Adapt size to fit the content. */
        this.setResizable(false);
        this.pack();
        // Center on screen.
        this.setLocationRelativeTo(null);
    }

    private JPanel buildContent() {
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
        layout.linkSize(constructButton, deconstructButton, clearPrefsButton);

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

        addComponent(constructButton, sequential, parallel);
        addComponent(deconstructButton, sequential, parallel);
        sequential.addPreferredGap(ComponentPlacement.UNRELATED);
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
