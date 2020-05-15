package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

import javax.swing.*;
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
            }
        });

        gui.updateBuild(new BuildViewMessage(PlayerIndex.PLAYER0, new Position(2,3), 1));
        gui.updatePutWorker(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(4,4), new Position(2,4)));
        gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,4), new Position(2,3)));
        gui.updateMoveWorker(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,3), new Position(3,4)));
        List<Position> actions = new ArrayList<>();
        actions.add(new Position(3,3));
        actions.add(new Position(2,3));
        actions.add(new Position(2,4));
        actions.add(new Position(4,3));
        actions.add(new Position(4,4));
        gui.updateActionView(new ActionMessage(PlayerIndex.PLAYER0, new Position(3,4), actions, ActionType.MOVE));

    }

    public static void main(String[] args){
        new GUIMain();
    }
}
