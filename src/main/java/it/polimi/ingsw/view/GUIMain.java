package it.polimi.ingsw.view;

import javax.swing.*;

public class GUIMain {

    private GUI gui;

    public GUIMain(){
        gui = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.initGUI();
            }
        });
    }

    public static void main(String[] args){
        new GUIMain();
    }
}
