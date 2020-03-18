package it.polimi.ingsw;

import org.junit.AfterClass;
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

            }
        }
    }

}
