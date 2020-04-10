package org.example;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {

    private static Cell cell;
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
            cell = null;
        }catch (NullPointerException e){
            assertEquals("",e.getMessage());
        }
    }

    @Test
    public void setDomeTest(){
        cellDome.setHasDome(true);
        cellNotDome.setHasDome(false);
        assertTrue(cellDome.hasDome());
        assertFalse(cellNotDome.hasDome());
    }
}