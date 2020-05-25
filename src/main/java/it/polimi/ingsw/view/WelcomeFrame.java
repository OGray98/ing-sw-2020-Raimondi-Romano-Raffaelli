package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {

    private static final Dimension FRAME_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int FRAME_WIDTH = (FRAME_DIMENSION.width/2)*3/2;
    private static final int FRAME_HEIGHT = (FRAME_DIMENSION.height / 2)*3/2;

    public WelcomeFrame(){
        super("");
        setLocation(FRAME_DIMENSION.width / 8, FRAME_DIMENSION.height / 8);
        setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
        setResizable(false);
        pack();

    }
}
