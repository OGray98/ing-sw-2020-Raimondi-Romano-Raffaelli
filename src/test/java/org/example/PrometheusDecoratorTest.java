package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusDecoratorTest {

    private static CardInterface cardPrometheus;
    private static PlayerInterface playerPrometheus;
    private static Board board;
    private static Position workerPosition;
    private static Position towerOnePosition;
    private static Position towerTwoPosition;


    @Before
    public void init(){
         cardPrometheus = new PrometheusDecorator();
         playerPrometheus = cardPrometheus.setPlayer(new Player("Jack",PlayerIndex.PLAYER0));
         board = new Board();
         workerPosition = new Position(1,1);
         towerOnePosition = new Position(0,0);
         towerTwoPosition = new Position(0,2);

    }

    @Test
    public void setChosenGodTest(){
        cardPrometheus.setChosenGod(true);
        assertTrue(cardPrometheus.getBoolChosenGod());
    }

    @Test
    public void canMoveAfterActivePowerTest(){
        board.constructBlock(towerOnePosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.setActivePower(true);
        assertTrue(playerPrometheus.getActivePower());
        assertTrue(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));
        assertFalse(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerOnePosition));
    }

    @Test
    public void canMoveAfterActivePowerTestPart2(){
        board.constructBlock(workerPosition);
        board.constructBlock(workerPosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.setActivePower(true);
        assertTrue(playerPrometheus.getActivePower());
        assertTrue(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));
        assertTrue(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerOnePosition));

    }

    @Test
    public void canMoveAfterNoActivePowerTest(){
        board.constructBlock(towerOnePosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.setActivePower(false);
        assertFalse(playerPrometheus.getActivePower());
        assertTrue(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));
        assertTrue(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerOnePosition));
    }

    @Test
    public void afterMoveTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.setActivePower(true);
        assertTrue(playerPrometheus.getActivePower());
        playerPrometheus.move(board.getCell(towerTwoPosition));
        assertFalse(playerPrometheus.getActivePower());
    }

    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.setActivePower(true);
        assertTrue(playerPrometheus.canUsePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));
        playerPrometheus.setActivePower(false);
        assertFalse(playerPrometheus.canUsePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));;
    }

    @Test
    public void usePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        BoardChange boardChange = playerPrometheus.usePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition);
        board.updateAfterPower(boardChange);
        assertEquals(1,board.getCell(towerTwoPosition).getLevel());
    }

}