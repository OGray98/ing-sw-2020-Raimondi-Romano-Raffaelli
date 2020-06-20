package it.polimi.ingsw.model.board;


import it.polimi.ingsw.exception.InvalidPositionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {


    private static Cell cellDome;
    private static Cell cellNotDome;


    @Before
    public void init(){
        cellDome = new Cell(1,1);
        cellNotDome = new Cell(2,1);
    }

    @Test
    public void isConstructCorrect(){
        try{
            new Cell(5,5);
        }catch(InvalidPositionException e){
            assertEquals("You cannot have a position in : [5][5]",e.getMessage());
        }
        try{
            new Cell(null);
        }catch (NullPointerException e){
            assertEquals("cell", e.getMessage());
        }
    }

    @Test
    public void setDomeTest() {
        cellDome.setHasDome(true);
        cellNotDome.setHasDome(false);
        assertTrue(cellDome.hasDome());
        assertFalse(cellNotDome.hasDome());
    }

    @Test
    public void toStringTest() {
        assertEquals("Cell{position=Position{row=2, col=1}, level=0, hasDome=false}", cellNotDome.toString());
    }

    @After
    public void afterAll() {
        cellDome = null;
        cellNotDome = null;
    }
}