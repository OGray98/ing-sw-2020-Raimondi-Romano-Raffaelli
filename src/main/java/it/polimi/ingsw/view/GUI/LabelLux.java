package it.polimi.ingsw.view.GUI;

import javax.swing.*;
import java.awt.*;

public class LabelLux extends JLabel {

    public LabelLux(Image image){
        super("");
        setIcon(new ImageIcon(image));
    }
}
