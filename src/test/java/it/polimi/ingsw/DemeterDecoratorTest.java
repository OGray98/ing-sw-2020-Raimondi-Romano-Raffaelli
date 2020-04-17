package it.polimi.ingsw;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(sameAsFirst));
        assertFalse(playerDemeter.canUsePower(power, board.getAdjacentPlayers(workerPosition)));
        List<Cell> power2 = new ArrayList<>();
        power2.add(new Cell(2,4));
        assertFalse(playerDemeter.canUsePower(power2, board.getAdjacentPlayers(workerPosition)));
        List<Cell> power3 = new ArrayList<>();
        power3.add(board.getCell(secondBuildPosition));
        assertTrue(playerDemeter.canUsePower(power3, board.getAdjacentPlayers(workerPosition)));
    }

    @Test
    public void usePowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(workerPosition), false);

        BoardChange powerResult = playerDemeter.usePower(board.getCell(secondBuildPosition));

        try{
            powerResult.getCantGoUp();
        }
        catch(NullPointerException e){
            assertEquals("cantGoUp", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());

        assertEquals(playerDemeter.usePower(board.getCell(secondBuildPosition)).getBuildType(), BuildType.LEVEL);
        assertEquals(playerDemeter.usePower(board.getCell(secondBuildPosition)).getPositionBuild(), secondBuildPosition);
        try{
            powerResult.getChanges();
        }
        catch(NullPointerException e){
            assertEquals("playerChanges", e.getMessage());
        }

        assertFalse(playerDemeter.getActivePower());
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerDemeter.getPowerListDimension());
    }

    @Test
    public void notUsedPowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setWorkerSituation(board.getCell(firstBuildingPosition), board.getCell(workerPosition), false);
        playerDemeter.activePowerAfterBuild();
        assertTrue(playerDemeter.getActivePower());

        board.putWorker(new Position(4,4),PlayerIndex.PLAYER2);
        playerDemeter.setStartingWorkerSituation(board.getCell(new Position(4,4)),false);
        assertFalse(playerDemeter.getActivePower());
    }

    @Test
    public void notUsedPowerTestPart2(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER2);
        playerDemeter.setWorkerSituation(board.getCell(firstBuildingPosition), board.getCell(workerPosition), false);
        playerDemeter.activePowerAfterBuild();
        assertTrue(playerDemeter.getActivePower());

        assertTrue(playerDemeter.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(new Position(2,1))));
        playerDemeter.move(board.getCell(new Position(2,1)));
        playerDemeter.setWorkerSituation(board.getCell(workerPosition),board.getCell(new Position(2,1)),false);
        assertFalse(playerDemeter.getActivePower());
    }
}
