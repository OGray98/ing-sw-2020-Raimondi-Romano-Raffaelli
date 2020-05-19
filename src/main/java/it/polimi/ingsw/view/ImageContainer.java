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
    private Map<String, Image> buttons;


    public ImageContainer(){
        gods = new HashMap<>();
        buttons = new HashMap<>();

        //TODO : aggiungere anche la mappa e i due pannelli?

        Image apollo = new ImageIcon("src/main/resources/god_cards/Apollo.png").getImage();
        Image artemis = new ImageIcon("src/main/resources/god_cards/Artemis.png").getImage();
        Image athena = new ImageIcon("src/main/resources/god_cards/Athena.png").getImage();
        Image atlas = new ImageIcon("src/main/resources/god_cards/Atlas.png").getImage();
        Image demeter = new ImageIcon("src/main/resources/god_cards/Demeter.png").getImage();
        Image hephaestus = new ImageIcon("src/main/resources/god_cards/Hephaestus.png").getImage();
        Image minotaur = new ImageIcon("src/main/resources/god_cards/Minotaur.png").getImage();
        Image pan = new ImageIcon("src/main/resources/god_cards/Pan.png").getImage();
        Image prometheus = new ImageIcon("src/main/resources/god_cards/Prometheus.png").getImage();

        //Buttons image
        Image moveButton = new ImageIcon("src/main/resources/castorpollux_bothMove.png").getImage();
        Image buildButton = new ImageIcon(this.getClass().getResource("/castorpollux_bothBuild.png")).getImage();
        Image powerButton = new ImageIcon(this.getClass().getResource("/heropower_active_small.png")).getImage();
        Image endTurnButton = new ImageIcon(this.getClass().getResource("/Chronus_ClockFace.png")).getImage();
        Image tutorialButton = new ImageIcon(this.getClass().getResource("/TutorialOn.png")).getImage();
        Image menuButton = new ImageIcon(this.getClass().getResource("/menu_button.png")).getImage();
        Image exitButton = new ImageIcon(this.getClass().getResource("/Exit.png")).getImage();



        gods.put("Apollo", apollo);
        gods.put("Artemis", artemis);
        gods.put("Athena", athena);
        gods.put("Atlas", atlas);
        gods.put("Demeter", demeter);
        gods.put("Hephaestus", hephaestus);
        gods.put("Minotaur", minotaur);
        gods.put("Pan", pan);
        gods.put("Prometheus", prometheus);

        buttons.put("buttonMove",moveButton);
        buttons.put("buttonBuild",buildButton);
        buttons.put("buttonPower",powerButton);
        buttons.put("buttonEndTurn",endTurnButton);
        buttons.put("buttonTutorial",tutorialButton);
        buttons.put("buttonMenu",menuButton);
        buttons.put("buttonExit",exitButton);


    }

    protected Image getGodimage(String godName){
        return gods.get(godName);
    }

    protected Image getButtonImage(String typeButton){ return buttons.get(typeButton);}



}
