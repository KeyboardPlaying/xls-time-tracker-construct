package org.keyboardplaying.construct.ui.components;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.keyboardplaying.construct.action.Action;

/**
 * A component to add an {@link Action} as a {@link JButton} on the interface.
 *
 * @author cyChop (http://keyboardplaying.org)
 */
public class ActionButton extends JButton implements ActionListener {

    private static final String IMG_PATH_PREFIX = "/icons/tango/16/";
    private static final String IMG_EXTENSION = ".png";

    private Action action;

    public ActionButton(Action action) {
        super(action.getLabel());
        this.action = action;

        try {
            Image img = ImageIO.read(getClass()
                    .getResource(IMG_PATH_PREFIX + action.getImageName() + IMG_EXTENSION));
            setIcon(new ImageIcon(img));
        } catch (IOException e) {
            // icons are inside source, this should not happen
        }

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
