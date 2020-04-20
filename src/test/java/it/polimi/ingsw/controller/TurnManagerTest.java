package it.polimi.ingsw.controller;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        board = new Board();
        domePos = new ArrayList<>(List.of(
                new Position(3, 3),
                new Position(4, 3),
                new Position(3, 4)
        ));
        level1Pos = new ArrayList<>(List.of(
                new Position(1, 0),
                new Position(4, 4)
        ));
        level2Pos = new ArrayList<>(List.of(
                new Position(2, 0),
                new Position(3, 4)
        ));
        level3Pos = new ArrayList<>(List.of(
                new Position(2, 3)
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
                new Position(0, 3),
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
        for (int i = 0; i < gods.size(); i++)
            gameInstance.setPlayerCard(gods.get(i));
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
        //endturn piu controllo sugli altri
    }

    @After
    public void tearDown() {
        Game.deleteInstance();
    }
}