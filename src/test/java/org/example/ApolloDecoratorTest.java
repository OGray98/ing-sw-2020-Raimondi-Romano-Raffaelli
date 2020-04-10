package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApolloDecoratorTest {

    CardInterface playerin;
    PlayerInterface playerint;
    private static Board board;
    private static Position workerPosition;
    private static Position newPosition;

    @Before
    public void init(){
        playerin = new ApolloDecorator();
        board = new Board();
        workerPosition = new Position(1,1);
        newPosition = new Position(1,2);
    }

    @Test
    public void test(){
        playerint = playerin.setPlayer(new Player("jack", PlayerIndex.PLAYER0));

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerint.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.changeWorkerPosition(workerPosition,newPosition);
        playerint.setAfterMove(board.getCell(workerPosition),board.getCell(newPosition));
        assertFalse(playerint.hasWin());
        assertFalse(playerin.getBoolChosenGod());
        playerin.setChosenGod(true);
        assertTrue(playerin.getBoolChosenGod());
        System.out.println(playerin.getGodName());
        System.out.println(playerin.getGodDescription());


    }

}