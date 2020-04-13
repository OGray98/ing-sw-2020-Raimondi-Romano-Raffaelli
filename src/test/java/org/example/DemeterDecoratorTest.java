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
    public void setChosenGodTest(){
        cardDemeter.setChosenGod(true);
        assertTrue(cardDemeter.getBoolChosenGod());
    }

    @Test
    public void canBuildDemeterTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        assertTrue(playerDemeter.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(firstBuildingPosition)));
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

        assertTrue(playerDemeter.canBuild(board.getAdjacentPlayers(workerPosition), new Cell(2,1)));
        assertTrue(playerDemeter.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(firstBuildingPosition)));

        assertFalse(playerDemeter.canUsePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), board.getCell(sameAsFirst)));
        assertFalse(playerDemeter.canUsePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), new Cell(2,4)));
        assertTrue(playerDemeter.canUsePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), board.getCell(secondBuildPosition)));
    }

    @Test
    public void usePowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        BoardChange powerResult = playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), board.getCell(secondBuildPosition));

        try{
            powerResult.getCantGoUp();
        }
        catch(NullPointerException e){
            assertEquals("cantGoUp", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());
        assertEquals(playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), board.getCell(secondBuildPosition)).getBuildType(), BuildType.LEVEL);
        assertEquals(playerDemeter.usePower(board.getAdjacentCells(workerPosition), board.getAdjacentPlayers(workerPosition), board.getCell(secondBuildPosition)).getPositionBuild(), secondBuildPosition);
        try{
            powerResult.getChanges();
        }
        catch(NullPointerException e){
            assertEquals("playerChanges", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());
    }
}
