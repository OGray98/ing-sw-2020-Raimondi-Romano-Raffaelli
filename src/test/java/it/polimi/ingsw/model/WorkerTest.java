package it.polimi.ingsw.model;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Test related to the Worker class and its methods
 * */

public class WorkerTest {

    private static Worker worker;
    private static Position position;
    private static final int player1 = 1;

    @BeforeClass
    public static void initWorkerP1(){
        worker = new Worker(1);
        position = new Position(0,0);
    }

    @Test
    public void isWorkerInit(){
        assertNull(worker.getOldPosition());
        assertNull(worker.getPositionOccupied());
        assertTrue(worker.getPlayerNum() == 1);

        worker.move(position);

        assertNull(worker.getOldPosition());
        assertNotNull(worker.getPositionOccupied());
        assertEquals(worker.getPositionOccupied(), position);
        assertEquals(worker.getPlayerNum(), 1);
    }

    @Test
    public void moveTest(){

        Position newPosition = new Position(1,1);

        worker.move(position);
        worker.move(newPosition);

        assertEquals(worker.getPositionOccupied(), newPosition);
        assertEquals(worker.getOldPosition(), position);
    }
}
