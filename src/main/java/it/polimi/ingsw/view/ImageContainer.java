package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class wich contains the more images of the app, they will be used by GUI
 * */
public class ImageContainer {

    //private Image background;
    //private Image labelTerminal;
    //private Image labelGod;
    private Map<String, Image> gods;

    public ImageContainer(){
        gods = new HashMap<>();

        Image apollo = new ImageIcon("src/main/resources/god_cards/Apollo.png").getImage();
        Image artemis = new ImageIcon("src/main/resources/god_cards/Artemis.png").getImage();
        Image athena = new ImageIcon("src/main/resources/god_cards/Athena.png").getImage();
        Image atlas = new ImageIcon("src/main/resources/god_cards/Atlas.png").getImage();
        Image demeter = new ImageIcon("src/main/resources/god_cards/Demeter.png").getImage();
        Image hephaestus = new ImageIcon("src/main/resources/god_cards/Hephaestus.png").getImage();
        Image minotaur = new ImageIcon("src/main/resources/god_cards/Minotaur.png").getImage();
        Image pan = new ImageIcon("src/main/resources/god_cards/Pan.png").getImage();
        Image prometheus = new ImageIcon("src/main/resources/god_cards/Prometheus.png").getImage();

        gods.put("Apollo", apollo);
        gods.put("Artemis", artemis);
        gods.put("Athena", athena);
        gods.put("Atlas", atlas);
        gods.put("Demeter", demeter);
        gods.put("Hephaestus", hephaestus);
        gods.put("Minotaur", minotaur);
        gods.put("Pan", pan);
        gods.put("Prometheus", prometheus);
    }

    protected Image getGodimage(String godName){
        return gods.get(godName);
    }

}
