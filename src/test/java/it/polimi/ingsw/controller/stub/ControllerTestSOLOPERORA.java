package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import it.polimi.ingsw.stub.StubObservableMessageReceiver;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.RemoteView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControllerTestSOLOPERORA {
    private final GameManager gameManager = new GameManager();
    private StubObservableMessageReceiver obs1;
    private StubObservableMessageReceiver obs2;
    private StubObservableMessageReceiver obs3;
    private Game game;
    private RemoteView remoteView1;
    private RemoteView remoteView2;
    private RemoteView remoteView3;

   /* @Test
    public void isCorr() {
        obs1 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER0, "Pasquale"));
        obs2 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER1, "Tony"));
        obs3 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER2, "PiccoloPietro"));
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
        obs1.setMsg(new NicknameMessage(PlayerIndex.PLAYER0, names.get(0)));
        obs1.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER0, true));
        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(1)));
        obs3.setMsg(new NicknameMessage(PlayerIndex.PLAYER2, names.get(2)));
        List<PlayerInterface> players = gameManager.getPlayers();
        List<String> actualNames = new ArrayList<>(0);
        players.forEach(player -> actualNames.add(player.getNickname()));
        assertEquals(actualNames, names);
        List<String> godChosen = new ArrayList<>(List.of("Demeter", "Athena", "Atlas"));
        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0, godChosen));
        //Check some god phase messages
        //God choose cards
        assertTrue(gameManager.getGame().getDeck().getChosenGodCards().size() == 3);
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        //players choose cards
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Athena"));
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        try{
            obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Apollo"));
        }
        catch(WrongGodNameException e){
            assertEquals("There isn't a god named Apollo", e.getMessage());
        }
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Athena"));
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER2);
        obs3.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Demeter"));
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER0);
        //godlike chooses first player
        obs1.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER2));
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER2);
        //Testing put worker phase
        obs3.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER2, new Position(0,0), new Position(0,1)));
        assertEquals(gameManager.getGame().getBoard().workerPositions(PlayerIndex.PLAYER2).size(), 2);
        assertEquals(gameManager.getGame().getBoard().getOccupiedPlayer(new Position(0,0)), PlayerIndex.PLAYER2);
        assertEquals(gameManager.getGame().getBoard().getOccupiedPlayer(new Position(0,1)), PlayerIndex.PLAYER2);
        //Check current player is updated
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER0);
        //if a player wants to put workers in occupied cell it can't do it
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,0)));
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(0,3)));
        //second player put his workers
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,4)));
        assertEquals(gameManager.getGame().getBoard().workerPositions(PlayerIndex.PLAYER0).size(), 2);
        //a player try to put workers when it is not his turn
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(3,3), new Position(3,4)));
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(3,3)));
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(3,4)));
        //last player put workers
        obs2.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER1, new Position(1,3), new Position(1,4)));
        assertEquals(gameManager.getGame().getBoard().workerPositions(PlayerIndex.PLAYER1).size(), 2);
        /*Initail board:
        *
        * P2    P2    0    P0   P0
        * 0     0     0    P1   P1
        * 0     0     0    0    0
        * 0     0     0    0    0
        * 0     0     0    0    0
        * */
        /*
        assertEquals(gameManager.getGame().getCurrentState(), GameState.MOVE);
        //Testing move phase
        assertEquals(gameManager.getGame().getCurrentPlayerIndex(), PlayerIndex.PLAYER2);
        //can't move in an enemy turn
        obs1.setMsg(new MoveMessage(PlayerIndex.PLAYER0, new Position(0, 3), new Position(1, 2)));
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(1,2)));
        //can't move if workerPos does not contains player worker
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(4, 4), new Position(1, 1)));
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(1,2)));
        //player can't move if the movement is not valid
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(0, 0), new Position(2, 2)));
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(2,2)));
        //Normal move
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(0, 0), new Position(1, 0)));
        assertEquals(gameManager.getGame().getBoard().getOccupiedPlayer(new Position(1,0)), PlayerIndex.PLAYER2);
        assertTrue(gameManager.getGame().getBoard().isFreeCell(new Position(0,0)));
        assertEquals(gameManager.getGame().getCurrentState(), GameState.BUILD);
        */
    //TODO: tests of handler for build, usepower, endturn!




        /*
        obs1 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER0, "Pasquale"));
        obs2 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER1, "Tony"));
        obs3 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER2, "PiccoloPietro"));
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
        obs1.setMsg(new NicknameMessage(PlayerIndex.PLAYER0, names.get(0)));
        obs1.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER0, true));
        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(1)));
        obs3.setMsg(new NicknameMessage(PlayerIndex.PLAYER2, names.get(2)));
        game = gameManager.getGameModel();
        for (int i = 0; i < 3; i++)
            assertEquals(names.get(i), game.getPlayers().get(i).getNickname());
        //Check some god phase messages
        //God choose cards
        List<String> godsChosen= new ArrayList<>(List.of("Artemis", "Minotaur", "Atlas"));
        obs2.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER1, godsChosen));
        assertFalse(game.getDeck().getChosenGodCards().size() == 3);
        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0, godsChosen));
        assertTrue(game.getDeck().getChosenGodCards().size() == 3);
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        //players choose cards
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Artemis"));
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        try{
            obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Apollo"));
        }
        catch(WrongGodNameException e){
            assertEquals("There isn't a god named Apollo", e.getMessage());
        }
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Artemis"));
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);
        obs3.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Minotaur"));
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        //godlike chooses first player
        obs1.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER2));
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);
        //Testing put worker phase
        obs3.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER2, new Position(0,0), new Position(0,1)));
        assertEquals(game.getBoard().workerPositions(PlayerIndex.PLAYER2).size(), 2);
        assertEquals(game.getBoard().getOccupiedPlayer(new Position(0,0)), PlayerIndex.PLAYER2);
        assertEquals(game.getBoard().getOccupiedPlayer(new Position(0,1)), PlayerIndex.PLAYER2);
        //Check current player is updated
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER0);
        //if a player wants to put workers in occupied cell it can't do it
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,0)));
        assertTrue(game.getBoard().isFreeCell(new Position(0,3)));
        //second player put his workers
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,4)));
        assertEquals(game.getBoard().workerPositions(PlayerIndex.PLAYER0).size(), 2);
        //a player try to put workers when it is not his turn
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(3,3), new Position(3,4)));
        assertTrue(game.getBoard().isFreeCell(new Position(3,3)));
        assertTrue(game.getBoard().isFreeCell(new Position(3,4)));
        //last player put workers
        obs2.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER1, new Position(1,3), new Position(1,4)));
        assertEquals(game.getBoard().workerPositions(PlayerIndex.PLAYER1).size(), 2);
        */

    //}

}
