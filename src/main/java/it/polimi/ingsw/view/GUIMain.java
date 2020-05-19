package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIMain {

    private GUI gui;

    public GUIMain(){
        this.gui = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.initGUI();
        }});

        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 1));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,4), new Position(2,4)));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER1, new Position(1,4), new Position(0,0)));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER2, new Position(3,4), new Position(0,1)));
        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 2));
        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 3));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 4));
        gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,4), new Position(2,3)));
        gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,3), new Position(2,2)));
        gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,2), new Position(3,1)));

        List<Position> actions = new ArrayList<>();
        actions.add(new Position(3,3));
        actions.add(new Position(4,3));
        actions.add(new Position(4,4));
        actions.add(new Position(2,1));
        gui.updateActionView(new ActionMessage(PlayerIndex.PLAYER0, new Position(3,4), actions, ActionType.MOVE));
        gui.updateSelectedCardView(new PlayerSelectGodMessage(PlayerIndex.PLAYER0, "Pan"));

    }

    public static void main(String[] args) /*throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException*/{
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new GUIMain();
        //temporaneo, brutale inserimento di immagini in GodChoiceDialog
        List<JLabel> gods = new ArrayList<>();
        Image image1 = new ImageIcon(("src/main/resources/_0000s_0001_god_and_hero_cards_0059_Castor_and_Pollux.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        JLabel god1 = new JLabel();
        god1.setIcon(new ImageIcon(image1));
        god1.setLayout(new BorderLayout());
        gods.add(god1);
        Image image2 = new ImageIcon(("src/main/resources/_0000s_0004_god_and_hero_cards_0052_Prometheus.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        JLabel god2 = new JLabel();
        god2.setIcon(new ImageIcon(image2));
        god2.setLayout(new BorderLayout());
        gods.add(god2);
        Image image3 = new ImageIcon(("src/main/resources/_0000s_0004_god_and_hero_cards_0052_Prometheus.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        JLabel god3= new JLabel();
        god3.setIcon(new ImageIcon(image3));
        god3.setLayout(new BorderLayout());
        gods.add(god3);
        Image image4 = new ImageIcon(("src/main/resources/_0000s_0004_god_and_hero_cards_0052_Prometheus.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        JLabel god4= new JLabel();
        god4.setIcon(new ImageIcon(image4));
        god4.setLayout(new BorderLayout());
        gods.add(god4);
        Image image5 = new ImageIcon(("src/main/resources/_0000s_0001_god_and_hero_cards_0059_Castor_and_Pollux.png")).getImage().getScaledInstance(150,230,Image.SCALE_DEFAULT);
        JLabel god5 = new JLabel();
        god5.setIcon(new ImageIcon(image5));
        god5.setLayout(new BorderLayout());
        gods.add(god5);
        //new GodChoiceDialog(new JFrame(), gods);
    }
}