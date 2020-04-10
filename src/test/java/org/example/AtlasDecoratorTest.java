package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasDecoratorTest {

    private static CardInterface atlasPlayer;
    private static PlayerInterface playerint;
    private static Board board;
    private static Position workerPos;
    private static Position posTrue;
    private static Position posFalse;

    @Before
    public void init(){
        atlasPlayer = new AtlasDecorator();
        board = new Board();
        workerPos = new Position(1,1);
        posTrue = new Position(2,2);
        posFalse = new Position(3,3);
        playerint = atlasPlayer.setPlayer(new Player("jack", PlayerIndex.PLAYER1));
    }

    @Test
    public void canBuildAtlasTest(){
        board.putWorker(workerPos, PlayerIndex.PLAYER1);
        playerint.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertTrue(playerint.canBuild(board.getAdjacentCells(workerPos), board.getAdjacentPlayers(workerPos), posTrue));
        assertFalse(playerint.canBuild(board.getAdjacentCells(workerPos), board.getAdjacentPlayers(workerPos), posFalse));

        assertTrue(playerint.getActivePower());
    }

    @Test
    public void usePowerAtlasTest(){
        board.putWorker(workerPos, PlayerIndex.PLAYER1);
        playerint.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertEquals(playerint.usePower(board.getAdjacentCells(playerint.getCellOccupied().getPosition()), board.getAdjacentPlayers(playerint.getCellOccupied().getPosition()), posTrue).getBuildType(), BuildType.DOME);
        assertEquals(playerint.usePower(board.getAdjacentCells(playerint.getCellOccupied().getPosition()), board.getAdjacentPlayers(playerint.getCellOccupied().getPosition()), posTrue).getPositionBuild(), posTrue);
        assertNull(playerint.usePower(board.getAdjacentCells(playerint.getCellOccupied().getPosition()), board.getAdjacentPlayers(playerint.getCellOccupied().getPosition()), posTrue).getCanGoUp());

        assertFalse(playerint.getActivePower());
    }

}
