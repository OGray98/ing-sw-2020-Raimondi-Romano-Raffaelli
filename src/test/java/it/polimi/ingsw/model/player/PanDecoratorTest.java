package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanDecoratorTest {

    private static Board board;
    private static PanDecorator pan;
    private static int indexWorker;


    @Before
    public void init() {
        board = new Board();
        pan = new PanDecorator(new Player(board, "Jack", 0));
        Position pos11 = new Position(1, 1);
        indexWorker = 0;
        pan.setSelectedWorker(indexWorker);
        pan.putWorker(pos11, 0);
    }

    @Test
    public void isHasWinCorrected() {

        Position pos12 = new Position(1, 2);
        Position pos13 = new Position(1, 3);
        Position pos14 = new Position(1, 4);
        Position pos23 = new Position(3, 3);

        //Go at first floor
        board.updateBoardBuild(pos12);
        pan.moveWorker(pos12);

        //Go at zero floor
        pan.moveWorker(new Position(1, 1));
        assertFalse(pan.hasWin());

        //Go at first floor
        pan.moveWorker(pos12);

        //Go at 2nd floor
        board.updateBoardBuild(pos13);
        board.updateBoardBuild(pos13);
        pan.moveWorker(pos13);

        //Go at zero floor
        pan.moveWorker(pos23);
        assertEquals(0, board.getCell(pos23).getLevel());
        assertEquals(CellOccupation.PLAYER1, board.getCell(pos23).getOccupation());
        assertEquals(pos23, pan.getWorkerPositionOccupied(indexWorker));
        assertTrue(pan.hasWin());

        //Go at first floor
        pan.moveWorker(pos12);
        //Go at 2nd floor
        pan.moveWorker(pos13);

        //Go at 3rd floor
        board.updateBoardBuild(pos14);
        board.updateBoardBuild(pos14);
        board.updateBoardBuild(pos14);
        pan.moveWorker(pos14);
        assertEquals(2, board.getCell(pos13).getLevel());
        assertEquals(3, board.getCell(pos14).getLevel());
        assertEquals(CellOccupation.PLAYER1, board.getCell(pos14).getOccupation());
        assertEquals(pos14, pan.getWorkerPositionOccupied(indexWorker));
        assertEquals(pos13, pan.getWorker(indexWorker).getOldPosition());
        assertTrue(pan.hasWin());

        //Go at zero floor
        pan.moveWorker(new Position(2, 4));
        assertTrue(pan.hasWin());
    }

}
