package org.keyboardplaying.construct.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.keyboardplaying.construct.action.Action;
import org.keyboardplaying.construct.ui.i18n.MessageBundle;

/**
 * A component to add an {@link Action} as a {@link JButton} on the interface.
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ActionButton extends IconButton implements ActionListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -8879808162137270769L;

    private Action action;

    /**
     * Creates a new instance.
     *
     * @param action
     *            the action to be performed when this button is clicked
     */
    public ActionButton(Action action) {
        super(new MessageBundle().getMessage(action.getLabelKey()), action.getIconImageName());
        this.action = action;

        addActionListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (!action.perform()) {
                JOptionPane.showMessageDialog(null, action.getUnsuccessMessage(), "Failure",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Throwable t) {
            StringBuilder msg = new StringBuilder(action.getUnsuccessMessage());
            msg.append('\n').append('\n').append(t.getClass().getName());
            String tMsg = t.getMessage();
            if (tMsg != null && tMsg.length() > 0) {
                msg.append('\n').append('\t').append(tMsg);
            }
            JOptionPane.showMessageDialog(null, msg.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
