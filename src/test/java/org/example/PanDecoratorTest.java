package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanDecoratorTest {

    private static CardInterface cardPan;
    private static PlayerInterface playerPan;
    private static Board board;
    private static Position workerPosition;
    private static Position buildPositionLevel1;
    private static Position buildPositionLevel2;
    private static Position buildPositionLevel3;

    @Before
    public void init(){
        cardPan = new PanDecorator();
        board = new Board();
        playerPan = cardPan.setPlayer(new Player("jack"));
        workerPosition = new Position(1,1);
        buildPositionLevel1 = new Position(1,2);
        buildPositionLevel2 = new Position(2,2);
        buildPositionLevel3 = new Position(2,1);

    }


    @Test
    public void hasWinTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPan.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.constructBlock(buildPositionLevel1);
        board.changeWorkerPosition(workerPosition,buildPositionLevel1);
        playerPan.setWorkerSituation(board.getCell(workerPosition),board.getCell(buildPositionLevel1),false);
        board.changeWorkerPosition(buildPositionLevel1,workerPosition);// 1 -> 0
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(workerPosition),false);
        assertFalse(playerPan.hasWin());
        board.constructBlock(buildPositionLevel2);
        board.constructBlock(buildPositionLevel2);
        board.changeWorkerPosition(workerPosition,buildPositionLevel1);
        playerPan.setWorkerSituation(board.getCell(workerPosition),board.getCell(buildPositionLevel1),false);
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel1);// 2 -> 1
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel1),false);
        assertFalse(playerPan.hasWin());
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,workerPosition);// 2 -> 0
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(workerPosition),false);
        assertTrue(playerPan.hasWin());
        board.constructBlock(buildPositionLevel3);
        board.constructBlock(buildPositionLevel3);
        board.constructBlock(buildPositionLevel3);
        board.changeWorkerPosition(workerPosition,buildPositionLevel1);
        playerPan.setWorkerSituation(board.getCell(workerPosition),board.getCell(buildPositionLevel1),false);
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel3);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel3),false);
        board.changeWorkerPosition(buildPositionLevel3,buildPositionLevel2);// 3 -> 2
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel3),board.getCell(buildPositionLevel2),false);
        assertFalse(playerPan.hasWin());
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel3);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel3),false);
        board.changeWorkerPosition(buildPositionLevel3,buildPositionLevel1);// 3 -> 1
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel3),board.getCell(buildPositionLevel1),false);
        assertTrue(playerPan.hasWin());
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel3);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel3),false);
        board.changeWorkerPosition(buildPositionLevel3,workerPosition); // 3 -> 0
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel3),board.getCell(workerPosition),false);
        assertTrue(playerPan.hasWin());


    }
}