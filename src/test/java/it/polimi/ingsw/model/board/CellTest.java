package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.InvalidIncrementLevelException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {
    private static Cell cell;

    @Before
    public void initCell() {

        try {
            cell = new Cell(0, 5);
        } catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [0][5]", e.getMessage());
        }
        try {
            cell = new Cell(5, 4);
        } catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [5][4]", e.getMessage());
        }
        try {
            cell = new Cell(-1, -2);
        } catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [-1][-2]", e.getMessage());
        }

        cell = new Cell(1, 4);
    }

    @Test
    public void isInitCell(){
        assertEquals(0, cell.getLevel());
        assertEquals(cell.getPosition(), new Position(1, 4));
        assertFalse(cell.isOccupied());
    }

    @Test
    public void occupiedTest(){
        cell.setOccupation(CellOccupation.EMPTY);
        assertFalse(cell.isOccupied());
        cell.setOccupation(CellOccupation.PLAYER1);
        assertTrue(cell.isOccupied() && cell.getOccupation() == CellOccupation.PLAYER1);
        cell.setOccupation(CellOccupation.PLAYER2);
        assertTrue(cell.isOccupied() && cell.getOccupation() == CellOccupation.PLAYER2);
        cell.setOccupation(CellOccupation.PLAYER3);
        assertTrue(cell.isOccupied() && cell.getOccupation() == CellOccupation.PLAYER3);
        cell.setOccupation(CellOccupation.DOME);
        assertTrue(cell.isOccupied() && cell.getOccupation() == CellOccupation.DOME);
    }

    @Test
    public void incLevelTest(){
        cell.incrementLevel();
        assertEquals(1, cell.getLevel());

        cell.setOccupation(CellOccupation.PLAYER1);
        try {
            cell.incrementLevel();
        } catch (InvalidIncrementLevelException e) {
            assertEquals("You cannot build in cell : [1][4]", e.getMessage());
        }
        cell.setOccupation(CellOccupation.EMPTY);

        cell.incrementLevel();
        cell.incrementLevel();
        assertEquals(3, cell.getLevel());
        cell.incrementLevel();
        assertEquals(3, cell.getLevel());
        assertEquals(CellOccupation.DOME, cell.getOccupation());

        try {
            cell.incrementLevel();
        } catch (InvalidIncrementLevelException e) {
            assertEquals("You cannot build in cell : [1][4]", e.getMessage());
        }
    }
}
