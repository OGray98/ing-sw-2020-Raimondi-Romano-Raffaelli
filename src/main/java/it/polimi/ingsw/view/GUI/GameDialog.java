package it.polimi.ingsw.view.GUI;

import javax.swing.*;
import java.awt.*;

public abstract class GameDialog extends JDialog {


    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public GameDialog(JFrame frame, String title) {
        super(frame, title);
        setPreferredSize(new Dimension(520, 315));
        setLocation(screenSize.width / 4, screenSize.height / 4);
        setResizable(false);
        pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //setMinimumSize(new Dimension(screenSize.width / 6, screenSize.height / 3));
        setVisible(true);
    }



}
