package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AthenaDecoratorTest {

    private static CardInterface cardAthena;
    private static PlayerInterface playerAthena;
    private static Board board;
    private static Position workerPos;
    private static Position lvlUpPos;
    private static Position notLvlUpPos;

    @Before
    public void init(){
        board = new Board();
        cardAthena = new AthenaDecorator();
        playerAthena = cardAthena.setPlayer(new Player("Jack", PlayerIndex.PLAYER0));
        workerPos = new Position(3,3);
        lvlUpPos = new Position(3,4);
        notLvlUpPos = new Position(4,3);
        board.constructBlock(lvlUpPos);
    }

    @Test
    public void setChosenGodTest(){
        cardAthena.setChosenGod(true);
        assertTrue(cardAthena.getBoolChosenGod());
    }

    @Test
    public void moveAthenaTest(){
        board.putWorker(workerPos, PlayerIndex.PLAYER0);
        playerAthena.setStartingWorkerSituation(board.getCell(workerPos), false);
        playerAthena.move(board.getCell(notLvlUpPos));

        assertFalse(playerAthena.getActivePower());

        playerAthena.move(board.getCell(lvlUpPos));

        assertTrue(playerAthena.getActivePower());
    }

    @Test
    public void canUsePowerAthenaTest(){
        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(workerPos));
        assertTrue(playerAthena.canUsePower(powers, board.getAdjacentPlayers(workerPos)));
    }

    @Test
    public void usePowerAthenaTest(){
        board.putWorker(workerPos, PlayerIndex.PLAYER0);
        playerAthena.setStartingWorkerSituation(board.getCell(workerPos), false);

        BoardChange powerResult = playerAthena.usePower(board.getCell(lvlUpPos));

        assertTrue(powerResult.getCantGoUp());

        try{
            powerResult.getChanges();
        }
        catch(NullPointerException e){
            assertEquals("playerChanges", e.getMessage());
        }

        try{
            powerResult.getPositionBuild();
        }
        catch(NullPointerException e){
            assertEquals("positionBuild", e.getMessage());
        }

        assertEquals(powerResult.getBuildType(), BuildType.LEVEL);

        assertFalse(playerAthena.getActivePower());
    }
}
