package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.FullChosenCardsException;
import it.polimi.ingsw.exception.NotEnoughGodsForPlayerException;
import it.polimi.ingsw.exception.NotPutTwoWorkerInSamePositionException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class GodPhaseManagerTest {


    private static  Game game;
    private static List<PlayerInterface> players;
    private static GodPhaseManager godManager;


    @Before
    public void init(){

        players = new ArrayList<>(3);
        players.add(new Player("Jack", PlayerIndex.PLAYER0));
        players.add(new Player("Creed", PlayerIndex.PLAYER1));
        players.add(new Player("Rock", PlayerIndex.PLAYER2));
        game = new Game(3);
        players.forEach(playerInterface -> game.addPlayer(playerInterface.getPlayerNum(), playerInterface.getNickname()));
        godManager = new GodPhaseManager(game);

    }


    @Test
    public void getPlayerIndexTest(){

        assertEquals(PlayerIndex.PLAYER0,godManager.getGodLikePlayerIndex());
        assertEquals(PlayerIndex.PLAYER0,game.getCurrentPlayerIndex());
    }

    /**
     * Test if player God like choose the corrected number of the Gods, and the corrected name of each chosen divinities
     */
    @Test
    public void godLikeChooseCardTest(){


        try{
            godManager.godLikeChooseCards("Aapoollo");
        }catch (WrongGodNameException e){
            assertEquals("There isn't a god named Aapoollo",e.getMessage());
        }

        godManager.godLikeChooseCards("Apollo");
        assertEquals("Apollo", godManager.getGodsChosen().get(0));
        godManager.godLikeChooseCards("Pan");
        assertEquals("Pan", godManager.getGodsChosen().get(1));
        godManager.godLikeChooseCards("Demeter");
        assertEquals("Demeter", godManager.getGodsChosen().get(2));

        try{
            godManager.godLikeChooseCards("Atlas");
        }catch (FullChosenCardsException e){
            assertEquals("The deck of chosen god is full",e.getMessage());
        }

    }

    /**
     * Verify if th chosen god boolean are set correctly
     */
    @Test
    public void setBoolGodLikeException() {


        godManager.godLikeChooseCards("Apollo");
        godManager.godLikeChooseCards("Pan");
        try {
            godManager.setBoolGodLike();
        } catch (NotEnoughGodsForPlayerException e) {
            assertEquals("There aren't enough gods for player, missed: " + (game.getNumPlayer() - godManager.getGodsChosen().size()), e.getMessage());
        }
        godManager.godLikeChooseCards("Demeter");
        godManager.setBoolGodLike();
        assertTrue(game.getDeck().getGodCard("Apollo").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Pan").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Demeter").getBoolChosenGod());

    }

    /**
     * Test if the god selected from each player is assigned correctly
     */
    @Test
    public void playerChooseGodTest(){


        godManager.godLikeChooseCards("Apollo");
        assertEquals("Apollo", godManager.getGodsChosen().get(0));
        godManager.godLikeChooseCards("Prometheus");
        assertEquals("Prometheus", godManager.getGodsChosen().get(1));
        godManager.godLikeChooseCards("Demeter");
        assertEquals("Demeter", godManager.getGodsChosen().get(2));

        godManager.setBoolGodLike();

        try{
            godManager.playerChooseGod("Aappollo");
        } catch (WrongGodNameException e){
            assertEquals("There isn't a god named Aappollo",e.getMessage());
        }

        godManager.playerChooseGod("Demeter");

        godManager.playerChooseGod("Apollo");

        godManager.playerChooseGod("Prometheus");
        assertEquals("Prometheus",game.getCurrentPlayerGodName());

        assertTrue(godManager.isFinishSelectCardPhase());

    }


    /**
     * Test if the order of player after choosing the first is correctly
     */
    @Test
    public void chooseGodLikeFirstPlayerTest(){


        godManager.godLikeChooseFirstPlayer(PlayerIndex.PLAYER1);
        assertEquals(PlayerIndex.PLAYER1,game.getSortedIndexes().get(0));
        assertEquals(PlayerIndex.PLAYER2,game.getSortedIndexes().get(1));
        assertEquals(PlayerIndex.PLAYER0,game.getSortedIndexes().get(2));
    }

    /**
     * Test if the player put theirs workers on the board correctly
     */
    @Test
    public void puttingWorkerTest(){


        Position samePos = new Position(1,1);
        try{
            godManager.puttingWorkerInBoard(samePos,samePos);
        } catch (NotPutTwoWorkerInSamePositionException e){
            assertEquals("You can't put the workers in same position : [" + samePos.row + "][" + samePos.col + "]",e.getMessage());
        }

        Position pos1 = new Position(2,2);
        Position pos2 = new Position(3,3);
        godManager.godLikeChooseFirstPlayer(PlayerIndex.PLAYER1);
        godManager.puttingWorkerInBoard(pos1,pos2);
        assertEquals(PlayerIndex.PLAYER1,game.getBoard().getOccupiedPlayer(pos1));
        assertEquals(PlayerIndex.PLAYER1,game.getBoard().getOccupiedPlayer(pos2));


        Position pos3 = new Position(1,1);
        Position pos4 = new Position(1,2);
        godManager.puttingWorkerInBoard(pos3,pos4);
        assertEquals(PlayerIndex.PLAYER2,game.getBoard().getOccupiedPlayer(pos3));
        assertEquals(PlayerIndex.PLAYER2,game.getBoard().getOccupiedPlayer(pos4));

        Position pos5= new Position(0,1);
        Position pos6 = new Position(0,2);
        godManager.puttingWorkerInBoard(pos5,pos6);
        assertEquals(PlayerIndex.PLAYER0,game.getBoard().getOccupiedPlayer(pos5));
        assertEquals(PlayerIndex.PLAYER0,game.getBoard().getOccupiedPlayer(pos6));

    }

}