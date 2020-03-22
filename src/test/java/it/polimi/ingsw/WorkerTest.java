package it.polimi.ingsw;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;

/*
* Test related to the Worker class and its methods
* */

public class WorkerTest {

    private static Worker worker;
    private static Cell cell;
    private static final CellOccupation player1 = CellOccupation.PLAYER1;

    @BeforeClass
    public static void initWorkerP1(){
        worker = new Worker(player1);
        cell = new Cell(0,0);
    }

    @Test
    public void isWorkerInit(){
        //assertNull(worker.getCellOccupied());

        worker.setCellOccupied(cell);

        assertNotNull(worker.getCellOccupied());
        assertEquals(worker.getPlayerNum(), CellOccupation.PLAYER1);
        assertTrue(worker.getCellOccupied().isOccupied());
        assertEquals(worker.getCellOccupied().getOccupation(), worker.getPlayerNum());
    }

    @Test
    public void moveTest(){

        Cell oldCell = new Cell(0,0);
        worker.setCellOccupied(oldCell);

        assertEquals(worker.getPlayerNum(), oldCell.getOccupation());

        worker.move(cell);

        assertEquals(worker.getCellOccupied(), cell);
        assertEquals(worker.getCellOccupied().getOccupation(), worker.getPlayerNum());
        //Apollo maybe can violate the following assertion
        assertEquals(oldCell.getOccupation(), CellOccupation.EMPTY);
    }

    @Test
    public void buildTest(){

        int beforeBuildLevel = cell.getLevel();
        Cell cellOccupied = new Cell(0, 1);
        worker.setCellOccupied(cellOccupied);

        worker.build(cell);

        if(beforeBuildLevel < 3){
            assertEquals(cell.getLevel(), beforeBuildLevel+1);
        }
        else if(beforeBuildLevel == 3){
            assertTrue(cell.getLevel() == 3 && cell.getOccupation() == CellOccupation.DOME);
        }
        assertEquals(worker.getCellOccupied(), cellOccupied);
    }
}
