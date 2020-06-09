package it.polimi.ingsw.view.GUI;

import javax.swing.*;
import java.awt.*;

public class PrincipalLabel extends JLabel {


    public PrincipalLabel(Image image,BorderLayout layout){
        super("");
        setIcon(new ImageIcon(image));
        setLayout(layout);
    }

    public PrincipalLabel(Image image){
        super("");
        setIcon(new ImageIcon(image));
    }
}
