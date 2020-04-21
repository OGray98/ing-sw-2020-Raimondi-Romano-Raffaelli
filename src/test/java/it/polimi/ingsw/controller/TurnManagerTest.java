package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.NotAdjacentMovementException;
import it.polimi.ingsw.exception.NotPresentWorkerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TurnManagerTest {

    private static TurnManager turnManager;
    private static Game gameInstance;
    private static Board board;
    private static List<PlayerInterface> players;
    private static List<Position> domePos;
    private static List<Position> level1Pos;
    private static List<Position> level2Pos;
    private static List<Position> level3Pos;
    private static List<String> gods;
    private static List<Position> workerPos;

    @Before
    public void setUp() {
        /*
            0  0A 0  2  0
            1D 0A 0  3C 2
            2  0  0B 3  0
            0  0  0  0D 2D
            0B 0  0  0D 1C
         */
        board = new Board();
        domePos = new ArrayList<>(List.of(
                new Position(3, 3),
                new Position(4, 3),
                new Position(1, 0),
                new Position(3, 4)
        ));
        level1Pos = new ArrayList<>(List.of(
                new Position(1, 0),
                new Position(4, 4)
        ));
        level2Pos = new ArrayList<>(List.of(
                new Position(2, 0),
                new Position(3, 4),
                new Position(0, 3),
                new Position(1, 4)
        ));
        level3Pos = new ArrayList<>(List.of(
                new Position(2, 3),
                new Position(1, 3)
        ));

        players = new ArrayList<>(3);
        players.add(new Player("Jack", PlayerIndex.PLAYER0));
        players.add(new Player("Rock", PlayerIndex.PLAYER1));
        players.add(new Player("Creed", PlayerIndex.PLAYER2));

        level1Pos.forEach(pos -> board.constructBlock(pos));
        level2Pos.forEach(pos -> {
            board.constructBlock(pos);
            board.constructBlock(pos);
        });
        level3Pos.forEach(pos -> {
            board.constructBlock(pos);
            board.constructBlock(pos);
            board.constructBlock(pos);
        });
        domePos.forEach(pos -> {
            Cell c = new Cell(board.getCell(pos));
            c.setHasDome(true);
            board.setCell(c);
        });

        workerPos = new ArrayList<>(List.of(
                new Position(1, 1),
                new Position(0, 1),
                new Position(2, 2),
                new Position(0, 4),
                new Position(1, 4),
                new Position(4, 4)
        )
        );

        for (int i = 0; i < 6; i++) {
            board.putWorker(workerPos.get(i), PlayerIndex.values()[i / 2]);
        }

        gameInstance = Game.createInstance(players, board);

        gods = new ArrayList<>(List.of("Atlas", "Apollo", "Prometheus"));
        Collections.rotate(gods, -1);
        gameInstance.setGodsChosenByGodLike(gods);
        for (String god : gods) gameInstance.setPlayerCard(god);
        Collections.rotate(gods, 1);
        gameInstance.chooseFirstPlayer(PlayerIndex.PLAYER0);

        turnManager = new TurnManager(gameInstance);
    }

    @Test
    public void constructTest() {
        try {
            new TurnManager(null);
        } catch (NullPointerException e) {
            assertEquals("gameInstance", e.getMessage());
        }
    }

    @Test
    public void canCurrentPlayerMoveAWorkerTest() {
        turnManager.startTurn();
        assertTrue(turnManager.canCurrentPlayerMoveAWorker());
        gameInstance.endTurn();
        gameInstance.endTurn();
        turnManager.startTurn();
        assertFalse(turnManager.canCurrentPlayerMoveAWorker());
    }

    @Test
    public void movementPositionsTest() {
        Position w1 = new Position(1, 1);
        turnManager.startTurn();
        assertTrue(turnManager.canCurrentPlayerMoveAWorker());
        ArrayList<Position> expectedMovePos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 2),
                new Position(1, 2),
                new Position(2, 1)
        ));
        assertEquals(expectedMovePos, turnManager.movementPositions(w1));

        try {
            turnManager.movementPositions(null);
        } catch (NullPointerException e) {
            assertEquals("workerPos", e.getMessage());
        }
        try {
            turnManager.movementPositions(new Position(3, 3));
        } catch (NotPresentWorkerException e) {
            assertEquals("There isn't a worker of " + PlayerIndex.PLAYER0 + " in : [" + 3 + "][" + 3 + "]", e.getMessage());
        }
    }

    @Test
    public void isValidMovementTest() {
        turnManager.startTurn();
        Position w1 = new Position(1, 1);
        assertTrue(turnManager.canCurrentPlayerMoveAWorker());
        assertTrue(turnManager.isValidMovement(w1, new Position(2, 1)));
        assertFalse(turnManager.isValidMovement(w1, new Position(0, 1)));
        assertFalse(turnManager.isValidMovement(new Position(2, 1), new Position(0, 1)));

        try {
            turnManager.isValidMovement(null, new Position(2, 1));
        } catch (NullPointerException e) {
            assertEquals("workerPos", e.getMessage());
        }

        try {
            turnManager.isValidMovement(w1, null);
        } catch (NullPointerException e) {
            assertEquals("movePos", e.getMessage());
        }
    }

    @Test
    public void moveWorkerTest() {
        turnManager.startTurn();
        Position w1 = new Position(1, 1);
        Position newW1 = new Position(2, 1);
        assertTrue(turnManager.canCurrentPlayerMoveAWorker());
        assertTrue(turnManager.isValidMovement(w1, newW1));

        turnManager.moveWorker(w1, newW1);
        assertEquals(PlayerIndex.PLAYER0, gameInstance.getBoard().getOccupiedPlayer(newW1));
        assertTrue(gameInstance.getBoard().isFreeCell(w1));

        try {
            turnManager.moveWorker(null, newW1);
        } catch (NullPointerException e) {
            assertEquals("workerPos", e.getMessage());
        }

        try {
            turnManager.moveWorker(w1, null);
        } catch (NullPointerException e) {
            assertEquals("movePos", e.getMessage());
        }

        try {
            turnManager.moveWorker(new Position(2, 2), newW1);
        } catch (NotPresentWorkerException e) {
            assertEquals("There isn't a worker of " +
                    PlayerIndex.PLAYER0 + " in : [" + 2 + "][" + 2 + "]", e.getMessage());
        }

        try {
            turnManager.moveWorker(w1, new Position(3, 0));
        } catch (NotAdjacentMovementException e) {
            assertEquals("You can't move a player from Position :[" + w1.row + "][" + w1.col + "]" +
                    "to Position : [" + 3 + "][" + 0 + "]", e.getMessage());
        }
    }


    @After
    public void tearDown() {
        Game.deleteInstance();
    }
}