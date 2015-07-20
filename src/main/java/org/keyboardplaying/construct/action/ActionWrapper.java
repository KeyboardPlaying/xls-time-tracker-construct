package org.keyboardplaying.construct.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author cyChop (http://keyboardplaying.org/)
 */
public class ActionWrapper implements ActionListener {

    private final Action action;

    public ActionWrapper(Action action) {
        this.action = action;
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
