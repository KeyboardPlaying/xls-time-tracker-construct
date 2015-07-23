package org.keyboardplaying.construct.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

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
import org.keyboardplaying.construct.action.ConstructAction;
import org.keyboardplaying.construct.action.DeconstructAction;
import org.keyboardplaying.construct.model.Project;

/**
 * A utility class for building a window.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class WindowBuilder {
    private List<Action> actions = new ArrayList<>();
    private String title;

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

    public WindowBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public WindowBuilder addActions(Action... actions) {
        for (Action action : actions) {
            this.actions.add(action);
        }
        return this;
    }

    private JPanel buildContent() {
        Project project = new Project();

        this.addActions(new ConstructAction(project), new DeconstructAction(project));

        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);

        SequentialGroup vGroup = layout.createSequentialGroup();
        ParallelGroup hGroup = layout.createParallelGroup();

        Component chooser = new ProjectChooser(project);
        hGroup.addComponent(chooser);
        vGroup.addComponent(chooser);

        SequentialGroup seqGroup = layout.createSequentialGroup();
        ParallelGroup parGroup = layout.createParallelGroup();

        Component sizeRef = null;

        for (Action action : actions) {
            JButton btn = new JButton(action.getLabel());
            btn.addActionListener(new ActionWrapper(action));
            seqGroup.addComponent(btn);
            parGroup.addComponent(btn);
            if (sizeRef == null) {
                sizeRef = btn;
            } else {
                layout.linkSize(SwingConstants.HORIZONTAL, sizeRef, btn);
            }
        }

        // TODO adjust sizes of layout
        hGroup.addGroup(seqGroup);
        vGroup.addGroup(parGroup);

        layout.setHorizontalGroup(hGroup);
        layout.setVerticalGroup(vGroup);

        return pane;
    }

    public JFrame build() {
        /* Title and icon. */
        JFrame window = new JFrame(title);

        /* Make sure thread is ended on close. */
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* General styling */
        // setAlwaysOnTop(alwaysOnTop);
        window.setResizable(false);
        // center on screen
        window.setLocationRelativeTo(null);

        /* Now the content. */
        window.setContentPane(buildContent());

        /* Adapt size to fit the content. */
        window.pack();

        return window;
    }
}
