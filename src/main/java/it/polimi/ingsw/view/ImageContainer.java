package it.polimi.ingsw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
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
    private Image blueLight;
    private Image borderGod;
    private Image purpleLight;
    private Image[] towerImage = new Image[4];
    private Image[] imageColorPlayer = new Image[3];
    private Image imageLabelTerminalPlayerNick;
    private Image imageWinner;


    public ImageContainer(){
        gods = new HashMap<>();
        buttons = new HashMap<>();

        Image apollo = new ImageIcon("src/main/resources/god_cards/Apollo.png").getImage();
        Image artemis = new ImageIcon("src/main/resources/god_cards/Artemis.png").getImage();
        Image athena = new ImageIcon("src/main/resources/god_cards/Athena.png").getImage();
        Image atlas = new ImageIcon("src/main/resources/god_cards/Atlas.png").getImage();
        Image demeter = new ImageIcon("src/main/resources/god_cards/Demeter.png").getImage();
        Image hephaestus = new ImageIcon("src/main/resources/god_cards/Hephaestus.png").getImage();
        Image minotaur = new ImageIcon("src/main/resources/god_cards/Minotaur.png").getImage();
        Image pan = new ImageIcon("src/main/resources/god_cards/Pan.png").getImage();
        Image prometheus = new ImageIcon("src/main/resources/god_cards/Prometheus.png").getImage();
        Image zeus = new ImageIcon("src/main/resources/god_cards/Zeus.png").getImage();
        Image hestia = new ImageIcon("src/main/resources/god_cards/Hestia.png").getImage();

        //Buttons image
        Image moveButton = new ImageIcon("src/main/resources/castorpollux_bothMove.png").getImage();
        Image buildButton = new ImageIcon(this.getClass().getResource("/castorpollux_bothBuild.png")).getImage();
        Image powerButton = new ImageIcon(this.getClass().getResource("/heropower_active_small.png")).getImage();
        Image endTurnButton = new ImageIcon(this.getClass().getResource("/Chronus_ClockFace.png")).getImage();
        Image tutorialButton = new ImageIcon(this.getClass().getResource("/TutorialOn.png")).getImage();
        Image menuButton = new ImageIcon(this.getClass().getResource("/menu_button.png")).getImage();
        Image exitButton = new ImageIcon(this.getClass().getResource("/Exit.png")).getImage();

        Image tower1 = new ImageIcon("src/main/resources/frame_blue.png").getImage();
        Image tower2 = new ImageIcon("src/main/resources/frame_coral.png").getImage();
        Image tower3 = new ImageIcon("src/main/resources/frame_yellow.png").getImage();
        Image tower4 = new ImageIcon("src/main/resources/cm_bg.png").getImage();

        Image imageColorBlue = new ImageIcon("src/main/resources/cm_btn_blue.png").getImage();
        Image imageColorRed = new ImageIcon("src/main/resources/cm_btn_coral.png").getImage();
        Image imageColorGray = new ImageIcon("src/main/resources/cm_btn_gray.png").getImage();

        imageWinner =  new ImageIcon("src/main/resources/endgame_victorywin.png").getImage();
        imageLabelTerminalPlayerNick = new ImageIcon("src/main/resources/cl_bg.png").getImage();

        imageColorPlayer[0] = imageColorBlue;
        imageColorPlayer[1] = imageColorRed;
        imageColorPlayer[2] = imageColorGray;

        towerImage[0] = tower1;
        towerImage[1] = tower2;
        towerImage[2] = tower3;
        towerImage[3] = tower4;

        gods.put("Apollo", apollo);
        gods.put("Artemis", artemis);
        gods.put("Athena", athena);
        gods.put("Atlas", atlas);
        gods.put("Demeter", demeter);
        gods.put("Hephaestus", hephaestus);
        gods.put("Minotaur", minotaur);
        gods.put("Pan", pan);
        gods.put("Prometheus", prometheus);
        gods.put("Zeus", zeus);
        gods.put("Hestia", hestia);

        buttons.put("buttonMove",moveButton);
        buttons.put("buttonBuild",buildButton);
        buttons.put("buttonPower",powerButton);
        buttons.put("buttonEndTurn",endTurnButton);
        buttons.put("buttonTutorial",tutorialButton);
        buttons.put("buttonMenu",menuButton);
        buttons.put("buttonExit",exitButton);

        blueLight = new ImageIcon("src/main/resources/playermoveindicator_blue.png").getImage();
        borderGod = new ImageIcon("src/main/resources/clp_frame_gold.png").getImage();
        purpleLight = new ImageIcon("src/main/resources/playermoveindicator_purple.png").getImage();





    }

    protected Image getGodimage(String godName){
        return gods.get(godName);
    }

    protected Image getButtonImage(String typeButton){ return buttons.get(typeButton);}

    protected Image getBlueLight(){
        return blueLight;
    }

    protected Image getBorderGod(){
        return borderGod;
    }

    protected Image getPurpleLight(){ return purpleLight;}

    protected Image getTowerLevel(int level){
        return towerImage[level];
    }

    protected Image getButtonColorPlayer(int numPlayerIndex) { return imageColorPlayer[numPlayerIndex];}

    protected Image getPlayersTerminalNick () { return imageLabelTerminalPlayerNick;}

    protected Image getImageWinner () { return imageWinner;}
}