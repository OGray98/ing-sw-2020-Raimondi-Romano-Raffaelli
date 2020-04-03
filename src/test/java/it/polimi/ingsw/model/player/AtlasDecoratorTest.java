package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.WorkerNotPresentException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AtlasDecoratorTest {

    PlayerInterface player;
    private static Position posTrue;
    private static Board board;
    private static Position posWorker;
    private static Position posFalse;



    @Before
    public void setUp() throws Exception {

        board = new Board();
        posWorker = new Position(1,1);
        player = new AtlasDecorator(new Player(board,"rock",1));
        posTrue = new Position(2,2);
        posFalse = new Position(3,3);

    }

    @Test
    public void canBuildAtlasTest() {

        player.putWorker(posWorker,0);
        assertTrue(player.canBuild(player.getBoard().getAdjacentCells(player.getWorkerPositionOccupied(0)),posTrue));
        assertFalse(player.canBuild(player.getBoard().getAdjacentCells(player.getWorkerPositionOccupied(0)),posFalse));

    }


}