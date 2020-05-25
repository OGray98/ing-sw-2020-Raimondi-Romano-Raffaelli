package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;

public abstract class GameDialog extends JDialog {

    private final JPanel panel = new JPanel();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int DIALOG_WIDTH = (screenSize.width/4)*3/2;
    private final int DIALOG_HEIGHT = (screenSize.height / 4)*3/2;

    public GameDialog(JFrame frame, String title) {
        super(frame, title);
        setPreferredSize(new Dimension(520, 315));
        setLocation(screenSize.width / 4, screenSize.height / 4);
        setResizable(false);
        pack();
        //setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setVisible(true);
    }

    public final int getDIALOG_WIDTH(){
        return DIALOG_WIDTH;
    }

    public final int getDIALOG_HEIGHT(){
        return DIALOG_HEIGHT;
    }

}
