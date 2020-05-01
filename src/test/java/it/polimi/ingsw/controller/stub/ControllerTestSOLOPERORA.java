package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.stub.StubObservableClientConnection;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.RemoteView;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControllerTestSOLOPERORA {
    private StubObservableClientConnection obs1;
    private StubObservableClientConnection obs2;
    private StubObservableClientConnection obs3;
    private Game game = new Game();
    private final GameManager gameManager = new GameManager(game);
    private RemoteView remoteView1;
    private RemoteView remoteView2;
    private RemoteView remoteView3;

    @Test
    public void isCorr() {

        obs1 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER0, "Pasquale"));
        obs2 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER1, "Tony"));
        obs3 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER2, "PiccoloPietro"));
        game.addObserver(obs1);
        game.addObserver(obs2);
        game.addObserver(obs3);
        remoteView1 = new RemoteView(PlayerIndex.PLAYER0, obs1);
        remoteView2 = new RemoteView(PlayerIndex.PLAYER1, obs2);
        remoteView3 = new RemoteView(PlayerIndex.PLAYER2, obs3);
        remoteView1.addObserver(gameManager);
        remoteView2.addObserver(gameManager);
        remoteView3.addObserver(gameManager);
        gameManager.addRemoteView(PlayerIndex.PLAYER0, remoteView1);
        gameManager.addRemoteView(PlayerIndex.PLAYER1, remoteView2);
        gameManager.addRemoteView(PlayerIndex.PLAYER2, remoteView3);

        List<String> names = new ArrayList<>(List.of("Tony", "Pasquale", "PiccoloPietro"));
        obs1.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER0, true));
        obs1.setMsg(new NicknameMessage(PlayerIndex.PLAYER0, names.get(0)));

        assertEquals(obs1.getMesRemoteToView().size(), 1);
        NicknameMessage nick = (NicknameMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(nick.getNickname(), "Tony");

        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(1)));

        assertEquals(obs2.getMesRemoteToView().size(), 1);
        NicknameMessage nick2 = (NicknameMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(nick2.getNickname(), "Pasquale");

        obs3.setMsg(new NicknameMessage(PlayerIndex.PLAYER2, names.get(2)));

        NicknameMessage nick3 = (NicknameMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(nick3.getNickname(), "PiccoloPietro");

        List<String> godChosen = new ArrayList<>(List.of("Demeter", "Athena", "Atlas"));
        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0, godChosen));

        GodLikeChoseMessage gods = (GodLikeChoseMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(gods.getGodNames(), godChosen);

        //Check some god phase messages
        //God choose cards
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Athena"));

        assertEquals(obs2.getMesRemoteToView().size(), 0);
        try{
            obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Apollo"));
        }
        catch(WrongGodNameException e){
            assertEquals("There isn't a god named Apollo", e.getMessage());
        }
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Athena"));

        PlayerSelectGodMessage god1 = (PlayerSelectGodMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(god1.getGodName(), "Athena");

        obs3.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Demeter"));

        PlayerSelectGodMessage god2 = (PlayerSelectGodMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(god2.getGodName(), "Demeter");

        //godlike chooses first player
        obs1.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER2));

        GodLikeChooseFirstPlayerMessage firstP = (GodLikeChooseFirstPlayerMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(firstP.getPlayerFirst(), PlayerIndex.PLAYER2);

        //Testing put worker phase
        obs3.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER2, new Position(0,0), new Position(0,1)));

        PutWorkerMessage put1 = (PutWorkerMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(put1.getPositionOne(), new Position(0,0));
        assertEquals(put1.getPositionTwo(), new Position(0,1));

        //if a player wants to put workers in occupied cell it can't do it
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,0)));
        assertEquals(obs1.getMesRemoteToView().size(), 0);

        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,4)));
        PutWorkerMessage put2 = (PutWorkerMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(put2.getPositionOne(), new Position(0,3));
        assertEquals(put2.getPositionTwo(), new Position(0,4));

        //a player try to put workers when it is not his turn
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(3,3), new Position(3,4)));
        assertEquals(obs1.getMesRemoteToView().size(), 0);

        //last player put workers
        obs2.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER1, new Position(1,3), new Position(1,4)));
        PutWorkerMessage put3 = (PutWorkerMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(put3.getPositionOne(), new Position(1,3));
        assertEquals(put3.getPositionTwo(), new Position(1,4));

        //Check that the new state is INITURN
        UpdateStateMessage initState = (UpdateStateMessage) obs2.getMesRemoteToView().get(1);
        assertEquals(initState.getGameState(), GameState.INITURN);

        /*Initial board:
         *
         * P2    P2    0    P0   P0
         * 0     0     0    P1   P1
         * 0     0     0    0    0
         * 0     0     0    0    0
         * 0     0     0    0    0
         * */

        //Testing move phase
        //can't move in an enemy turn
        obs1.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3)));
        assertEquals(obs1.getMesRemoteToView().size(),0);
        obs1.setMsg(new MoveMessage(PlayerIndex.PLAYER0, new Position(0, 3), new Position(1, 2)));
        assertEquals(obs1.getMesRemoteToView().size(),0);

        //can't move if workerPos does not contains player worker
        obs3.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER2, new Position(0,1)));
        ActionMessage select1 = (ActionMessage) obs3.getMesRemoteToView().get(2);
        assertEquals(select1.getWorkerPos(), new Position(0,1));
        //Check that the new state is MOVE
        UpdateStateMessage moveState = (UpdateStateMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(moveState.getGameState(), GameState.MOVE);

        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(4, 4), new Position(1, 1)));
        assertEquals(obs3.getMesRemoteToView().size(),0);

        //player can't move if the movement is not valid
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(0, 0), new Position(2, 2)));
        assertEquals(obs3.getMesRemoteToView().size(),0);

        //Normal move
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(0, 0), new Position(1, 0)));
        MoveMessage move1 = (MoveMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(move1.getWorkerPosition(), new Position(0,0));
        assertEquals(move1.getMovePosition(), new Position(1,0));
        //Check that the new state is BUILD
        UpdateStateMessage buildState = (UpdateStateMessage) obs3.getMesRemoteToView().get(1);
        assertEquals(buildState.getGameState(), GameState.BUILD);

        //TODO: tests of handler for build, usepower, endturn!


    }

}