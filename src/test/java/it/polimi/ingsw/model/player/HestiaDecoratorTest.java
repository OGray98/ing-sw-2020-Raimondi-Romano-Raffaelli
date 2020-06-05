package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HestiaDecoratorTest {
    private static Deck deck;
    private static CardInterface cardHestia;
    private static PlayerInterface playerHestia;
    private static Board board;
    private static Position workerPosition;
    private static Position firstBuildingPosition;
    private static Position perimeter;
    private static Position notPerimeter;

    @Before
    public void init() {
        deck = new Deck();
        board = new Board();
        cardHestia = deck.getGodCard("Hestia");
        playerHestia = cardHestia.setPlayer(new Player("Jack", PlayerIndex.PLAYER1));
        workerPosition = new Position(1, 1);
        firstBuildingPosition = new Position(1, 2);
        notPerimeter = new Position(1, 2);
        perimeter = new Position(0, 0);
    }

    /*Check if the card is no more available because it has been chosen*/
    @Test
    public void setChosenGodTest() {
        cardHestia.setChosenGod(true);
        assertTrue(cardHestia.getBoolChosenGod());
    }


    /*Check if canUsePower is correct in different cases*/
    @Test
    public void canUsePowerTest() {
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerHestia.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertTrue(playerHestia.canBuild(board.getAdjacentPlayers(workerPosition), new Cell(2, 1)));
        assertTrue(playerHestia.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(firstBuildingPosition)));

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(notPerimeter));
        assertTrue(playerHestia.canUsePower(power, board.getAdjacentPlayers(workerPosition)));
        List<Cell> power2 = new ArrayList<>();
        power2.add(new Cell(2, 4));
        assertFalse(playerHestia.canUsePower(power2, board.getAdjacentPlayers(workerPosition)));
        List<Cell> power3 = new ArrayList<>();
        power3.add(board.getCell(perimeter));
        assertFalse(playerHestia.canUsePower(power3, board.getAdjacentPlayers(workerPosition)));
    }

    /*Check usePower() exceptions and correctness*/
    @Test
    public void usePowerTest() {
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerHestia.setStartingWorkerSituation(board.getCell(workerPosition), false);

        BoardChange powerResult = playerHestia.usePower(board.getCell(perimeter));

        try {
            powerResult.getCantGoUp();
        } catch (NullPointerException e) {
            assertEquals("cantGoUp", e.getMessage());
        }

        assertEquals(playerHestia.usePower(board.getCell(perimeter)).getBuildType(), BuildType.LEVEL);
        assertEquals(playerHestia.usePower(board.getCell(perimeter)).getPositionBuild(), perimeter);
        try {
            powerResult.getChanges();
        } catch (NullPointerException e) {
            assertEquals("playerChanges", e.getMessage());
        }
    }

    //Simple check for the powerListDimension
    @Test
    public void getPowerListDimensionTest() {
        assertEquals(1, playerHestia.getPowerListDimension());
    }


    @After
    public void afterAll() {

        deck = null;
        board = null;
        playerHestia = null;
        cardHestia = null;
        workerPosition = null;
        firstBuildingPosition = null;
        notPerimeter = null;
        perimeter = null;


    }
}
