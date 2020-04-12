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
    public void setWorkerSituationTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.setWorkerSituation(null,board.getCell(workerPosition),false);
        assertTrue(playerPrometheus.getActivePower());
   }

   @Test
    public void moveTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.move(board.getCell(towerOnePosition));
        assertFalse(playerPrometheus.getCantGoUp());
    }

    @Test
    public void usePowerTest(){
        board.constructBlock(towerTwoPosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        BoardChange boardChange = playerPrometheus.usePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerOnePosition);
        assertEquals(towerOnePosition,boardChange.getPositionBuild());
        assertTrue(playerPrometheus.getCantGoUp());
        assertFalse(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));
        assertFalse(playerPrometheus.getActivePower());
        board.updateAfterPower(boardChange);
        assertEquals(1,board.getCell(towerOnePosition).getLevel());
        playerPrometheus.move(board.getCell(towerOnePosition));
        assertFalse(playerPrometheus.getCantGoUp());
        assertTrue(playerPrometheus.canMove(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),towerTwoPosition));

    }



}