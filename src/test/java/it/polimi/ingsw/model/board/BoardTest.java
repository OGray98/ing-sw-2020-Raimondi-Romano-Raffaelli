package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.CellNotFreeException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    private static Board board;

    private Cell getCell(int r, int c){
        return board.getCell(new Position(r, c));
    }

    @Before
    public void initBoard(){
        board = new Board();
    }

    @Test
    public void isBoardInit(){
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++) {
                assertFalse(getCell(i, j).isOccupied());
                assertEquals(0, getCell(i, j).getLevel());
                assertEquals(getCell(i, j).getOccupation(), CellOccupation.EMPTY);
                assertEquals(getCell(i, j).getPosition(), new Position(i, j));
                assertEquals(getCell(i, j), new Cell(i, j));

            }
        }
    }

    @Test
    public void isGetAdjacentListCorrected() {

        //Control every cases
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(isStandardCaseCorrected(board.getAdjacentCells(new Position(i, j)), i, j));
            }
        }

        //Control exception case
        int row = 2;
        int col = 5;
        try {
            board.getAdjacentCells(new Position(row, col));
        } catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [" + row + "][" + col + "]", e.getMessage());
        }
    }

    @Test
    public void isPutWorkerCorrected() {
        Position p00 = new Position(0, 0);
        board.putWorker(p00, 0);
        Position p01 = new Position(1, 3);
        board.putWorker(p01, 0);

        Position p10 = new Position(4, 2);
        board.putWorker(p10, 1);
        Position p11 = new Position(3, 3);
        board.putWorker(p11, 1);

        Position p20 = new Position(1, 2);
        board.putWorker(p20, 2);
        Position p21;
        try {
            p21 = new Position(1, 2);
            board.putWorker(p21, 2);
        } catch (CellNotFreeException e) {
            assertEquals("Cell in position [" + 1 + "][" + 2 + "] isn't free", e.getMessage());
        }
        p21 = new Position(2, 4);
        board.putWorker(p21, 2);

        assertSame(CellOccupation.PLAYER1, board.getCell(p00).getOccupation());
        assertSame(CellOccupation.PLAYER1, board.getCell(p01).getOccupation());
        assertSame(CellOccupation.PLAYER2, board.getCell(p10).getOccupation());
        assertSame(CellOccupation.PLAYER2, board.getCell(p11).getOccupation());
        assertSame(CellOccupation.PLAYER3, board.getCell(p20).getOccupation());
        assertSame(CellOccupation.PLAYER3, board.getCell(p21).getOccupation());
        assertSame(CellOccupation.EMPTY, board.getCell(new Position(0, 1)).getOccupation());

    }

    @Test
    public void isUpdateBoardMoveCorrected() {
        Position p00 = new Position(0, 0);
        board.putWorker(p00, 0);
        Position p01 = new Position(1, 3);
        board.putWorker(p01, 0);

        Position p10 = new Position(4, 2);
        board.putWorker(p10, 1);
        Position p11 = new Position(3, 3);
        board.putWorker(p11, 1);

        Position p20 = new Position(1, 2);
        board.putWorker(p20, 2);
        Position p21 = new Position(2, 4);

        board.putWorker(p21, 2);

        Position newP00 = new Position(0, 1);
        board.updateBoardMove(p00, newP00, 0);
        assertSame(CellOccupation.EMPTY, board.getCell(p00).getOccupation());
        assertSame(CellOccupation.PLAYER1, board.getCell(newP00).getOccupation());

        try {
            board.updateBoardMove(p10, p11, 1);
        } catch (CellNotFreeException e) {
            assertEquals("Cell in position [" + p11.row + "][" + p11.col + "] isn't free", e.getMessage());
        }
    }

    @Test
    public void isUpdateBoardBuildCorrected() {
        Position p00 = new Position(0, 0);
        board.putWorker(p00, 0);
        Position p01 = new Position(1, 3);
        board.putWorker(p01, 0);

        Position p10 = new Position(4, 2);
        board.putWorker(p10, 1);
        Position p11 = new Position(3, 3);
        board.putWorker(p11, 1);

        Position p20 = new Position(1, 2);
        board.putWorker(p20, 2);
        Position p21 = new Position(2, 4);
        board.putWorker(p21, 2);

        Position buildPosition = new Position(2,4);
        try {
            board.updateBoardBuild(buildPosition);
        } catch (CellNotFreeException e) {
            assertEquals("Cell in position [" + buildPosition.row + "][" + buildPosition.col + "] isn't free", e.getMessage());
        }

        buildPosition = null;
        try {
            board.updateBoardBuild(buildPosition);
        } catch (NullPointerException e) {
            assertEquals("buildPosition", e.getMessage());
        }

        buildPosition = new Position(4,4);

        for (int i = 0; i < 3; i++) {
            assertEquals(i, board.getCell(buildPosition).getLevel());
            board.updateBoardBuild(buildPosition);
        }
        assertEquals(3, board.getCell(buildPosition).getLevel());
        board.updateBoardBuild(buildPosition);
        assertEquals(CellOccupation.DOME, board.getCell(buildPosition).getOccupation());
    }

    @Test
    public void isUpdateBoardBuildDomeCorrected() {
        Position p00 = new Position(0, 0);
        board.putWorker(p00, 0);
        Position p01 = new Position(1, 3);
        board.putWorker(p01, 0);

        Position p10 = new Position(4, 2);
        board.putWorker(p10, 1);
        Position p11 = new Position(3, 3);
        board.putWorker(p11, 1);

        Position p20 = new Position(1, 2);
        board.putWorker(p20, 2);
        Position p21 = new Position(2, 4);
        board.putWorker(p21, 2);

        Position buildPosition = new Position(2,4);
        try {
            board.UpdateBoardBuildDome(buildPosition);
        } catch (CellNotFreeException e) {
            assertEquals("Cell in position [" + buildPosition.row + "][" + buildPosition.col + "] isn't free", e.getMessage());
        }

        buildPosition = null;
        try {
            board.UpdateBoardBuildDome(buildPosition);
        } catch (NullPointerException e) {
            assertEquals("position", e.getMessage());
        }

        buildPosition = new Position(4, 4);

        board.UpdateBoardBuildDome(buildPosition);
        assertEquals(0, board.getCell(buildPosition).getLevel());
        assertEquals(CellOccupation.DOME, board.getCell(buildPosition).getOccupation());
    }

    private boolean isCornerUpLeftCorrected(List<Cell> cells) {
        return cells.size() == 3 && getCell(0, 1).equals(cells.get(0)) && getCell(1, 0).equals(cells.get(1)) && getCell(1, 1).equals(cells.get(2));
    }

    private boolean isCornerDownRightCorrected(List<Cell> cells) {
        return cells.size() == 3 && getCell(3, 3).equals(cells.get(0)) && getCell(3, 4).equals(cells.get(1)) && getCell(4, 3).equals(cells.get(2));
    }

    private boolean isCornerUpRightCorrected(List<Cell> cells) {
        return cells.size()== 3 && getCell(0,3).equals(cells.get(0)) && getCell(1,3).equals(cells.get(1)) && getCell(1,4).equals(cells.get(2));
    }

    private boolean isCornerDownLeftCorrected(List<Cell> cells) {
        return cells.size()== 3 && getCell(3,0).equals(cells.get(0)) && getCell(3,1).equals(cells.get(1)) && getCell(4,1).equals(cells.get(2));
    }

    private boolean isBoundaryUpCorrected(List<Cell> cells, int c) {
        if (c == 0)
            return isCornerUpLeftCorrected(cells);
        if (c == 4)
            return isCornerUpRightCorrected(cells);
        return cells.size() == 5 && getCell(0, c-1).equals(cells.get(0)) && getCell(0, c+1).equals(cells.get(1)) && getCell(1, c-1).equals(cells.get(2))
                && getCell(1, c).equals(cells.get(3)) && getCell(1, c+1).equals(cells.get(4));
    }

    private boolean isBoundaryDownCorrected(List<Cell> cells, int c) {
        if (c == 0)
            return isCornerDownLeftCorrected(cells);
        if (c == 4)
            return isCornerDownRightCorrected(cells);
        return cells.size() == 5 && getCell(3, c-1).equals(cells.get(0)) && getCell(3, c).equals(cells.get(1)) && getCell(3, c+1).equals(cells.get(2))
                && getCell(4, c-1).equals(cells.get(3)) && getCell(4, c+1).equals(cells.get(4));
    }

    private boolean isBoundaryLeftCorrected(List<Cell> cells, int r) {
        if (r == 0)
            return isCornerUpLeftCorrected(cells);
        if (r == 4)
            return isCornerDownLeftCorrected(cells);
        return cells.size() == 5 && getCell(r-1, 0).equals(cells.get(0)) && getCell(r-1, 1).equals(cells.get(1)) && getCell(r, 1).equals(cells.get(2))
                && getCell(r+1, 0).equals(cells.get(3)) && getCell(r+1, 1).equals(cells.get(4));
    }

    private boolean isBoundaryRightCorrected(List<Cell> cells, int r) {
        if (r == 0)
            return isCornerUpRightCorrected(cells);
        if (r == 4)
            return isCornerDownRightCorrected(cells);
        return cells.size() == 5 && getCell(r-1, 3).equals(cells.get(0)) && getCell(r-1, 4).equals(cells.get(1)) && getCell(r, 3).equals(cells.get(2))
                && getCell(r+1, 3).equals(cells.get(3)) && getCell(r+1, 4).equals(cells.get(4));
    }

    private boolean isStandardCaseCorrected(List<Cell> cells, int r, int c) {
        if(r == 0)
            return isBoundaryUpCorrected(cells, c);
        else if(r == 4)
            return isBoundaryDownCorrected(cells, c);
        if(c == 0)
            return isBoundaryLeftCorrected(cells, r);
        else if(c == 4)
            return isBoundaryRightCorrected(cells, r);

        return cells.size() == 8 && getCell(r-1, c-1).equals(cells.get(0)) && getCell(r-1, c).equals(cells.get(1)) && getCell(r-1, c+1).equals(cells.get(2))
                && getCell(r, c-1).equals(cells.get(3)) && getCell(r, c+1).equals(cells.get(4))
                && getCell(r+1, c-1).equals(cells.get(5)) && getCell(r+1, c).equals(cells.get(6)) && getCell(r+1, c+1).equals(cells.get(7));
    }

}
