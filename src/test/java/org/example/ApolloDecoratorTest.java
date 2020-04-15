package org.example;

import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ApolloDecoratorTest {

    CardInterface cardApollo;
    PlayerInterface playerApollo;
    PlayerInterface playerOpponent;
    private static Board board;
    private static Position workerPosition;
    private static Position workerOpponentPosition;
    private static Position secondWorkerPosition;
    private static Position thirdWorkerPosition;
    private static Position secondOpponentWorkerPosition;
    private static Position towerWorkerTwo;
    private static Position towerWorkerOne;
    PlayerInterface otherPlayerOpponent;

    @Before
    public void init(){
        cardApollo = new ApolloDecorator();
        board = new Board();
        playerApollo = cardApollo.setPlayer(new Player("jack", PlayerIndex.PLAYER0));
        playerOpponent = new Player("Rocky",PlayerIndex.PLAYER1);
        workerPosition = new Position(1,1);
        workerOpponentPosition = new Position(1,2);
        secondWorkerPosition = new Position(0,0);
        thirdWorkerPosition = new Position(1,0);
        otherPlayerOpponent = new Player("Creed",PlayerIndex.PLAYER2);
        secondOpponentWorkerPosition = new Position(2,0);
        towerWorkerTwo = new Position(2,1);
        towerWorkerOne = new Position(0,2);

    }

    @Test
    public void setChosenGodTest(){
        cardApollo.setChosenGod(true);
        assertTrue(cardApollo.getBoolChosenGod());
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerApollo.getPowerListDimension());
    }

    @Test
    public void canMoveTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        board.putWorker(secondWorkerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(secondWorkerPosition),false);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);

        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(new Position(3,3))));
        assertFalse(playerApollo.getActivePower());
        assertTrue(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),new Cell(0,1)));
        assertFalse(playerApollo.getActivePower());
        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(secondWorkerPosition)));
        assertFalse(playerApollo.getActivePower());


        board.constructBlock(secondOpponentWorkerPosition);
        board.constructBlock(secondOpponentWorkerPosition);
        board.putWorker(secondOpponentWorkerPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(secondOpponentWorkerPosition),false);
        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(secondOpponentWorkerPosition)));
        assertFalse(playerApollo.getActivePower());

        board.constructBlock(towerWorkerTwo);
        board.constructBlock(towerWorkerTwo);
        board.constructBlock(towerWorkerOne);
        board.changeWorkerPosition(workerPosition,towerWorkerOne);
        playerApollo.setWorkerSituation(board.getCell(workerPosition),board.getCell(towerWorkerOne),false);
        board.changeWorkerPosition(towerWorkerOne,towerWorkerTwo);
        playerApollo.setWorkerSituation(board.getCell(towerWorkerOne),board.getCell(towerWorkerTwo),false);
        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(towerWorkerTwo),board.getCell(workerOpponentPosition)));
        assertTrue(playerApollo.getActivePower());

    }

    @Test
    public void canMoveTestPart2(){

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.constructBlock(thirdWorkerPosition);
        board.putWorker(thirdWorkerPosition,PlayerIndex.PLAYER2);
        otherPlayerOpponent.setStartingWorkerSituation(board.getCell(thirdWorkerPosition),false);
        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(thirdWorkerPosition)));
        assertTrue(playerApollo.getActivePower());

    }

    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerApollo.canMove(board.getAdjacentPlayers(workerPosition),new Cell(0,2));
        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(new Position(0,2)));
        assertFalse(playerApollo.canUsePower(powers,board.getAdjacentPlayers(workerPosition)));
        board.putWorker(thirdWorkerPosition,PlayerIndex.PLAYER2);
        otherPlayerOpponent.setStartingWorkerSituation(board.getCell(thirdWorkerPosition),false);
        playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(thirdWorkerPosition));
        List<Cell> powers1 = new ArrayList<>();
        powers1.add(board.getCell(thirdWorkerPosition));
        assertTrue(playerApollo.canUsePower(powers1,board.getAdjacentPlayers(workerPosition)));
    }

    @Test
    public void usePowerTest(){


        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerOpponentPosition));
        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(workerOpponentPosition)));
        assertTrue(playerApollo.getActivePower());
        assertTrue(playerApollo.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        BoardChange boardChange = playerApollo.usePower(board.getCell(workerOpponentPosition));
        board.updateAfterPower(boardChange);
        assertEquals(PlayerIndex.PLAYER0,board.getOccupiedPlayer(workerOpponentPosition));
        assertEquals(PlayerIndex.PLAYER1,board.getOccupiedPlayer(workerPosition));
    }

    @Test
    public void usePowerTestPart2(){
        board.constructBlock(workerPosition);
        board.constructBlock(workerPosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerOpponentPosition));
        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(workerOpponentPosition)));
        assertTrue(playerApollo.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        BoardChange boardChange = playerApollo.usePower(board.getCell(workerOpponentPosition));
        board.updateAfterPower(boardChange);
        assertEquals(PlayerIndex.PLAYER0,board.getOccupiedPlayer(workerOpponentPosition));
        assertEquals(PlayerIndex.PLAYER1,board.getOccupiedPlayer(workerPosition));


    }

    @Test
    public void twoCanMoveTest(){
        /*if player refuse to use power, active power must become false*/
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.constructBlock(thirdWorkerPosition);
        board.putWorker(thirdWorkerPosition,PlayerIndex.PLAYER2);
        otherPlayerOpponent.setStartingWorkerSituation(board.getCell(thirdWorkerPosition),false);


        assertFalse(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(thirdWorkerPosition)));
        assertTrue(playerApollo.getActivePower());

        assertTrue(playerApollo.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(new Position(0,1))));
        assertFalse(playerApollo.getActivePower());
    }

}