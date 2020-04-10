package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterDecoratorTest {

    private static CardInterface cardDemeter;
    private static PlayerInterface playerDemeter;
    private static Board board;
    private static Position workerPosition;
    private static Position firstBuildingPosition;
    private static Position secondBuildPosition;
    private static Position sameAsFirst;

    @Before
    public void init(){
        board = new Board();
        cardDemeter = new DemeterDecorator();
        playerDemeter = cardDemeter.setPlayer(new Player("Jack", PlayerIndex.PLAYER1));
        workerPosition = new Position(2,2);
        firstBuildingPosition = new Position(2,3);
        sameAsFirst = new Position(2,3);
        secondBuildPosition = new Position(3,2);
    }

    @Test
    public void canBuildDemeterTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertTrue(playerDemeter.canBuild(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), firstBuildingPosition));
    }

    @Test
    public void activePowerAfterBuildTest(){
        playerDemeter.activePowerAfterBuild();
        assertTrue(playerDemeter.getActivePower());
    }

    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertTrue(playerDemeter.canBuild(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), new Position(2,1)));
        assertTrue(playerDemeter.canBuild(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), firstBuildingPosition));

        assertFalse(playerDemeter.canUsePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), sameAsFirst));
        assertFalse(playerDemeter.canUsePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), new Position(2,4)));
        assertTrue(playerDemeter.canUsePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), secondBuildPosition));
    }

    @Test
    public void usePowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertNull(playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), secondBuildPosition).getCantGoUp());
        assertFalse(playerDemeter.getActivePower());
        assertEquals(playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), secondBuildPosition).getBuildType(), BuildType.LEVEL);
        assertEquals(playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), secondBuildPosition).getPositionBuild(), secondBuildPosition);
        try{
            playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), secondBuildPosition).getChanges();
        }
        catch(NullPointerException e){
            assertEquals("changes", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());
    }
}
