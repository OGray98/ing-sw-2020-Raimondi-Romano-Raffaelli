package it.polimi.ingsw.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/*
 * Test related to the Worker class and its methods
 * */

public class WorkerTest {

    private static Worker worker;
    private static Position position;
    private static final CellOccupation player1 = CellOccupation.PLAYER1;

    @BeforeClass
    public static void initWorkerP1(){
        worker = new Worker(player1);
        position = new Position(0,0);
    }

    @Test
    public void isWorkerInit(){
        //assertNull(worker.getCellOccupied());

        worker.setPositionOccupied(position);

        assertNotNull(worker.getPositionOccupied());
        assertEquals(worker.getPlayerNum(), CellOccupation.PLAYER1);
    }

    @Test
    public void moveTest(){

        Position oldPosition = new Position(0,0);
        worker.setPositionOccupied(oldPosition);

        worker.move(position);

        assertEquals(worker.getPositionOccupied(), position);
    }

    /*@Test
    public void buildTest(){
        int beforeBuildLevel = cell.getLevel();
        Cell cellOccupied = new Cell(0, 1);
        worker.setPositionOccupied(cellOccupied);
        worker.build(cell);
        if(beforeBuildLevel < 3){
            assertEquals(cell.getLevel(), beforeBuildLevel+1);
        }
        else if(beforeBuildLevel == 3){
            assertTrue(cell.getLevel() == 3 && cell.getOccupation() == CellOccupation.DOME);
        }
        assertEquals(worker.getPositionOccupied(), cellOccupied);
    }*/
}
