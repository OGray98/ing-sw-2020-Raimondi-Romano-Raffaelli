package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.After;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;

public class TurnManagerTest {

    private static Game gameInstance;
    private static Board board;
    private static List<PlayerInterface> players;
    private static HashMap<Position, Cell> cells;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @After
    public void tearDown() throws Exception {
    }
}