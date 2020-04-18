package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.DemeterDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DemeterDecoratorTest {

    private static Deck deck;
    private static CardInterface cardDemeter;
    private static PlayerInterface playerDemeter;
    private static Board board;
    private static Position workerPosition;
    private static Position firstBuildingPosition;
    private static Position secondBuildPosition;
    private static Position sameAsFirst;

    @Before
    public void init(){
        deck = new Deck(2);
        board = new Board();
        cardDemeter = deck.getGodCard("Demeter");
        playerDemeter = cardDemeter.setPlayer(new Player("Jack", PlayerIndex.PLAYER1));
        workerPosition = new Position(2,2);
        firstBuildingPosition = new Position(2,3);
        sameAsFirst = new Position(2,3);
        secondBuildPosition = new Position(3,2);
    }

    /*Check if the card is no more available because it has been chosen*/
    @Test
    public void setChosenGodTest(){
        cardDemeter.setChosenGod(true);
        assertTrue(cardDemeter.getBoolChosenGod());
    }

    //Simple check on the canBuild method
    @Test
    public void canBuildDemeterTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertTrue(playerDemeter.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(firstBuildingPosition)));
    }

    /*Check if canUsePower is correct in different cases*/
    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertTrue(playerDemeter.canBuild(board.getAdjacentPlayers(workerPosition), new Cell(2,1)));
        assertTrue(playerDemeter.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(firstBuildingPosition)));

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(sameAsFirst));
        assertFalse(playerDemeter.canUsePower(power, board.getAdjacentPlayers(workerPosition)));
        List<Cell> power2 = new ArrayList<>();
        power2.add(new Cell(2,4));
        assertFalse(playerDemeter.canUsePower(power2, board.getAdjacentPlayers(workerPosition)));
        List<Cell> power3 = new ArrayList<>();
        power3.add(board.getCell(secondBuildPosition));
        assertTrue(playerDemeter.canUsePower(power3, board.getAdjacentPlayers(workerPosition)));
    }

    /*Check usePower() exceptions and correctness*/
    @Test
    public void usePowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        BoardChange powerResult = playerDemeter.usePower(board.getCell(secondBuildPosition));

        try{
            powerResult.getCantGoUp();
        }
        catch(NullPointerException e){
            assertEquals("cantGoUp", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());

        assertEquals(playerDemeter.usePower(board.getCell(secondBuildPosition)).getBuildType(), BuildType.LEVEL);
        assertEquals(playerDemeter.usePower(board.getCell(secondBuildPosition)).getPositionBuild(), secondBuildPosition);
        try{
            powerResult.getChanges();
        }
        catch(NullPointerException e){
            assertEquals("playerChanges", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());
    }

    //Simple check for the powerListDimension
    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerDemeter.getPowerListDimension());
    }
}
