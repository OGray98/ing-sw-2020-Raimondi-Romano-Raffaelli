package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;

public abstract class GameDialog extends JDialog {

    private final JPanel panel = new JPanel();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public GameDialog(JFrame frame, String title) {
        super(frame, title);
        setPreferredSize(new Dimension((screenSize.width/4)*3/2, (screenSize.height / 4)*3/2));
        setLocation(screenSize.width / 4, screenSize.height / 4);
        setResizable(false);
        pack();
        //setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setVisible(true);
    }

}
