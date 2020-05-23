package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.BuildViewMessage;
import it.polimi.ingsw.utils.MoveMessage;
import it.polimi.ingsw.utils.PlayerSelectGodMessage;
import it.polimi.ingsw.utils.PutWorkerMessage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GUIMain {

    private final GUI gui;
    private final Client client;

    public GUIMain() {
        this.gui = new GUI(PlayerIndex.PLAYER0, new ClientModel());
        this.client = new Client("127.0.0.1", 1985);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.initGUI();
            }
        });

        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3, 1), 1));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0, 4), new Position(2, 4)));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER1, new Position(1,4), new Position(0,0)));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER2, new Position(3,4), new Position(0,1)));
        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 2));
        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 3));
        gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER1, new Position(0,0), new Position(3,1)));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 3));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER1,new Position(3,0),1));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER1,new Position(3,0),2));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(3,1), 4));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER1, new Position(0,0), new Position(3,1)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,4), new Position(3,0)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER1, new Position(1,4), new Position(3,0)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER2, new Position(0,1), new Position(3,0)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(3,0), new Position(3,1)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(3,0), new Position(1,4)));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER1,new Position(3,0),1));
        //gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER1,new Position(3,0),2));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER1, new Position(0,0), new Position(3,0)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER1, new Position(3,0), new Position(3,4)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER1, new Position(3,0), new Position(3,4)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(3,1), new Position(3,0)));
        //gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,2), new Position(3,1)));

        /*List<Position> actions = new ArrayList<>();
        actions.add(new Position(3,1));
        actions.add(new Position(4,3));
        actions.add(new Position(4,4));
        actions.add(new Position(2,1));
        gui.updateActionView(new ActionMessage(PlayerIndex.PLAYER0, new Position(3,4), actions, ActionType.MOVE));*/
        gui.updateSelectedCardView(new PlayerSelectGodMessage(PlayerIndex.PLAYER0, "Pan"));


    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new GUIMain();

        //creo lista di gods da visualizzare in GodChoiceDialog
        List<String> gods = new ArrayList<>(List.of("Apollo", "Athena", "Minotaur", "Prometheus"));
        //new GodChoiceDialog(new JFrame(), gods, new ImageContainer());
    }
}