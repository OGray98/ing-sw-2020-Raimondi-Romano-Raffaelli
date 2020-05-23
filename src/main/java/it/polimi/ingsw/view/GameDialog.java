package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;

public abstract class GameDialog extends JDialog {

    private final JPanel panel = new JPanel();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public GameDialog(JFrame frame, String title) {
        super(frame, title);
        setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        setPreferredSize(new Dimension(screenSize.width - screenSize.width / 3, screenSize.height / 2));
        setLocation(screenSize.width / 5, screenSize.height / 4);
        setResizable(false);
        add(panel, BorderLayout.NORTH);
        pack();
        setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setVisible(true);
    }

}
