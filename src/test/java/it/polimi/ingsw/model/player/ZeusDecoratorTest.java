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

public class ZeusDecoratorTest {

    private static Deck deck;
    private static CardInterface cardZeus;
    private static PlayerInterface playerZeus;
    private static Board board;
    private static Position workerPosition;

    @Before
    public void init() {
        deck = new Deck();
        board = new Board();
        cardZeus = deck.getGodCard("Zeus");
        playerZeus = cardZeus.setPlayer(new Player("Jack", PlayerIndex.PLAYER0));
        workerPosition = new Position(1, 1);

    }

    /*Check if the card is no more available because it has been chosen*/
    @Test
    public void setChosenGodTest() {
        cardZeus.setChosenGod(true);
        assertTrue(cardZeus.getBoolChosenGod());
    }

    @Test
    public void getPowerListDimensionTest() {
        assertEquals(1, playerZeus.getPowerListDimension());
    }

    @Test
    public void canUsePowerTest() {

        board.putWorker(workerPosition, PlayerIndex.PLAYER0);
        playerZeus.setStartingWorkerSituation(board.getCell(workerPosition), false);

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerPosition));
        assertTrue(playerZeus.canUsePower(power, board.getAdjacentPlayers(workerPosition)));
        assertFalse(playerZeus.canBuild(board.getAdjacentPlayers(workerPosition), power.get(0)));

        board.constructBlock(workerPosition);
        board.constructBlock(workerPosition);
        board.constructBlock(workerPosition);

        power = new ArrayList<>();
        power.add(board.getCell(workerPosition));
        assertFalse(playerZeus.canUsePower(power, board.getAdjacentPlayers(workerPosition)));
    }

    @Test
    public void usePowerTest() {
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerZeus.setStartingWorkerSituation(board.getCell(workerPosition), false);

        BoardChange powerResult = playerZeus.usePower(board.getCell(workerPosition));

        try {
            powerResult.getCantGoUp();
        } catch (NullPointerException e) {
            assertEquals("cantGoUp", e.getMessage());
        }

        assertEquals(powerResult.getBuildType(), BuildType.LEVEL);
        assertEquals(powerResult.getPositionBuild(), workerPosition);
        try {
            powerResult.getChanges();
        } catch (NullPointerException e) {
            assertEquals("playerChanges", e.getMessage());
        }
    }

    @After
    public void afterAll() {

        deck = null;
        board = null;
        playerZeus = null;
        cardZeus = null;
        workerPosition = null;

    }

}
