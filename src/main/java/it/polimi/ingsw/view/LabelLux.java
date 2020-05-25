package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;

public class LabelLux extends JLabel {

    public LabelLux(Image image){
        super("");
        setIcon(new ImageIcon(image));
    }
}
