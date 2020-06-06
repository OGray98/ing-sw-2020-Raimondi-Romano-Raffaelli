package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TritonDecoratorTest {
    private static CardInterface cardTriton;
    private static PlayerInterface playerTriton;
    private static Board board;
    private static Position workerPos;
    private static Position firstMovePosition;
    private static Position secondMovePosition;
    private static Deck deck;

    @Before
    public void init() {
        deck = new Deck();
        board = new Board();
        cardTriton = deck.getGodCard("Triton");
        playerTriton = cardTriton.setPlayer(new Player("Jack", PlayerIndex.PLAYER2));
        workerPos = new Position(1, 1);
        firstMovePosition = new Position(1, 2);
        secondMovePosition = new Position(0, 2);
    }

    @Test
    public void setChosenGodTest() {
        cardTriton.setChosenGod(true);
        assertTrue(cardTriton.getBoolChosenGod());
    }

    @Test
    public void getPowerListDimensionTest() {
        assertEquals(1, playerTriton.getPowerListDimension());
    }


    @Test
    public void canUsePowerTritonTest() {

        board.putWorker(workerPos, playerTriton.getPlayerNum());
        playerTriton.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertEquals(board.getOccupiedPlayer(workerPos), PlayerIndex.PLAYER2);

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerPos));
        assertFalse(playerTriton.canUsePower(power, board.getAdjacentPlayers(workerPos)));

        board.changeWorkerPosition(workerPos, firstMovePosition);
        playerTriton.move(board.getCell(firstMovePosition));

        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(workerPos));
        assertFalse(playerTriton.canUsePower(powers, board.getAdjacentPlayers(workerPos)));

        List<Cell> powers1 = new ArrayList<>();
        powers1.add(board.getCell(firstMovePosition));
        assertFalse(playerTriton.canUsePower(powers1, board.getAdjacentPlayers(firstMovePosition)));

        List<Cell> powers2 = new ArrayList<>();
        powers2.add(board.getCell(secondMovePosition));
        assertFalse(playerTriton.canUsePower(powers2, board.getAdjacentPlayers(secondMovePosition)));

        board.changeWorkerPosition(firstMovePosition, secondMovePosition);
        playerTriton.move(board.getCell(secondMovePosition));

        List<Cell> powers3 = new ArrayList<>();
        powers3.add(board.getCell(firstMovePosition));
        assertTrue(playerTriton.canUsePower(powers3, board.getAdjacentPlayers(firstMovePosition)));
    }


    @Test
    public void usePowerTritonTest() {

        board.putWorker(workerPos, playerTriton.getPlayerNum());
        playerTriton.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertEquals(board.getOccupiedPlayer(workerPos), PlayerIndex.PLAYER2);

        board.changeWorkerPosition(workerPos, secondMovePosition);
        playerTriton.move(board.getCell(secondMovePosition));

        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(firstMovePosition));
        assertTrue(playerTriton.canUsePower(powers, board.getAdjacentPlayers(secondMovePosition)));

        BoardChange powerResult = playerTriton.usePower(board.getCell(firstMovePosition));

        try {
            powerResult.getCantGoUp();
        } catch (NullPointerException e) {
            assertEquals("cantGoUp", e.getMessage());
        }

        assertEquals(powerResult.getChanges().size(), 1);
        PositionContainer positions = new PositionContainer(secondMovePosition);
        positions.put(firstMovePosition);
        boolean thereIs = false;
        for (Map.Entry<PositionContainer, PlayerIndex> entry : powerResult.getChanges().entrySet()) {
            if (entry.getKey().equals(positions)) {
                thereIs = true;
                break;
            }
        }
        assertTrue(thereIs);
        assertTrue(powerResult.getChanges().containsValue(playerTriton.getPlayerNum()));

    }


    @After
    public void afterAll() {

        deck = null;
        board = null;
        cardTriton = null;
        playerTriton = null;
        workerPos = null;
        firstMovePosition = null;
        secondMovePosition = null;
    }

}
