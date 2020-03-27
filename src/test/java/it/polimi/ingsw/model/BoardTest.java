package it.polimi.ingsw.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {
    private static Board board;

    private Cell getCell(int r, int c){
        return board.getCell(r, c);
    }

    @BeforeClass
    public static void initBoard(){
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
        /*
        //Corner case control
        assertTrue(isCornerUpLeftCorrected(board.getAdjacentCells(0,0)));
        assertTrue(isCornerUpRightCorrected(board.getAdjacentCells(0,4)));
        assertTrue(isCornerDownLeftCorrected(board.getAdjacentCells(4,0)));
        assertTrue(isCornerDownRightCorrected(board.getAdjacentCells(4,4)));

        //Boundary case control
        assertTrue(isBoundaryUpCorrected(board.getAdjacentCells(0,1), 1));
        assertTrue(isBoundaryDownCorrected(board.getAdjacentCells(4,1), 1));
        assertTrue(isBoundaryLeftCorrected(board.getAdjacentCells(2,0), 2));
        assertTrue(isBoundaryRightCorrected(board.getAdjacentCells(3,4), 3));

        //Standard case control
        assertTrue(isStandardCaseCorrected(board.getAdjacentCells(3,1), 3,1));
        */

        //Control every cases
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(isStandardCaseCorrected(board.getAdjacentCells(i,j), i,j));
            }
        }

        //Control exception case
        int row = 2;
        int col = 5;
        try {
            board.getAdjacentCells(row, col);
        } catch (IllegalArgumentException e) {
            assertEquals("There can't be a cell in [" + row + "][" + col + "]", e.getMessage());
        }
    }

    private boolean isCornerUpLeftCorrected(List<Cell> cells) {
        return cells.size()== 3 && getCell(0,1).equals(cells.get(0)) && getCell(1,0).equals(cells.get(1)) && getCell(1,1).equals(cells.get(2));
    }

    private boolean isCornerDownRightCorrected(List<Cell> cells) {
        return cells.size()== 3 && getCell(3,3).equals(cells.get(0)) && getCell(3,4).equals(cells.get(1)) && getCell(4,3).equals(cells.get(2));
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
