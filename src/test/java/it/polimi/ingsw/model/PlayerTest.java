package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void isInitPlayer() {

        Player player = new Player("Jack", 1);

        assertSame(player.getWorker(0).getPlayerNum(), CellOccupation.PLAYER1);
        assertSame(player.getWorker(1).getPlayerNum(), CellOccupation.PLAYER1);
        assertEquals("Jack", player.getNickName());
    }

    @Test
    public void putWorkerTest(){

        Player player = new Player("Jack", 1);
        Cell cell = new Cell(0,0);

        //Workers are initialized with no cellOccupied
        assertNull(player.getWorker(0).getCellOccupied());
        assertNull(player.getWorker(1).getCellOccupied());

        player.putWorker(cell, 0);

        //After putWorker(c, i) method the cellOccupied by the worker number i must be not null and must be the cell c
        assertNotNull(player.getWorker(0).getCellOccupied());
        assertEquals(player.getWorker(0).getCellOccupied(), cell);

    }

    @Test
    public void moveWorkerTest(){

        Player player = new Player("Jack", 1);
        Board board = new Board();
        Position destinationPosition = new Position(1, 1);
        Cell destinationCell = board.getCell(destinationPosition);

        player.putWorker(board.getCell(new Position(0, 0)), 0);
        player.moveWorker(board, destinationPosition, 0);

        assertEquals(destinationCell.getOccupation(), CellOccupation.PLAYER1);
        assertEquals(player.getWorker(0).getCellOccupied(), destinationCell);
    }

    @Test
    public void buildTest() {

        Player player = new Player("Jack", 1);
        Board board = new Board();
        Position destinationPosition = new Position(1, 1);
        Cell destinationCell = board.getCell(destinationPosition);
        int beforeBuildLevel = destinationCell.getLevel();

        player.putWorker(board.getCell(new Position(0, 1)), 1);
        Position beforeBuildWorkerPosition = player.getWorkerPosition(1);

        player.build(board, destinationPosition, 1);

        if (destinationCell.getLevel() < 3) {
            assertEquals(destinationCell.getLevel(), beforeBuildLevel + 1);
        }
        else if(destinationCell.getLevel() == 3){
            assertTrue(destinationCell.getLevel() == 3 && destinationCell.getOccupation() == CellOccupation.DOME);
        }
        assertEquals(player.getWorkerPosition(1), beforeBuildWorkerPosition);
    }

    //TODO: Test per chooseGodPower

}
