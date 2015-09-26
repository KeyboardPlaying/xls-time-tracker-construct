package org.keyboardplaying.xtt.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.keyboardplaying.xtt.action.Action;

/**
 * A component to add an {@link Action} to a button.
 *
 * @param <T>
 *            the type of action this button is linked to
 *
 * @author Cyrille Chopelet (http://keyboardplaying.org)
 */
public class ActionButton<T extends Action> extends IconButton implements ActionListener {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -5878747183335075792L;

    private T action;

    /**
     * Sets the action for this instance.
     *
     * @param action
     *            the new action
     */
    public void setAction(T action) {
        this.action = action;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.keyboardplaying.xtt.ui.components.IconButton#init()
     */
    @Override
    public void init() {
        super.init();
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
