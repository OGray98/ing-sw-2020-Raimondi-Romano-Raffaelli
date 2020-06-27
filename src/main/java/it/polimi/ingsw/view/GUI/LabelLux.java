package it.polimi.ingsw.view.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Class that create a label with the image given to the constructor
 * */
public class LabelLux extends JLabel {

    public LabelLux(Image image){
        super("");
        setIcon(new ImageIcon(image));
    }
}
