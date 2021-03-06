package it.polimi.ingsw.view.GUI;

import javax.swing.*;

/**
 * Class of JLayeredPane used to draw images on different levels
 */
public class LevelPane extends JLayeredPane {

    Integer[] layerValues = { JLayeredPane.DEFAULT_LAYER,
            JLayeredPane.PALETTE_LAYER,
            JLayeredPane.MODAL_LAYER,
            JLayeredPane.POPUP_LAYER,
            JLayeredPane.DRAG_LAYER };

    public LevelPane(){
        super();
    }

    public int getLayerPane(int n){
        return layerValues[n];
    }









}
