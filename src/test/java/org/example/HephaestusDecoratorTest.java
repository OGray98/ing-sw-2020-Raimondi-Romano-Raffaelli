package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HephaestusDecoratorTest {


    private static CardInterface cardHephaestus;
    private static PlayerInterface playerHephaestus;
    private static Board board;
    private static Position workerPosition;
    private static Position buildPosition;
    private static Position otherPosition;
    private static BoardChange boardChange;

    @Before
    public void init(){
        board = new Board();
        cardHephaestus = new HephaestusDecorator();
        playerHephaestus = cardHephaestus.setPlayer(new Player("jack", PlayerIndex.PLAYER0));
        workerPosition = new Position(1,1);
        buildPosition = new Position(1,2);
        otherPosition = new Position(1,0);
    }

    @Test
    public void setChosenGodTest(){
        cardHephaestus.setChosenGod(true);
        assertTrue(cardHephaestus.getBoolChosenGod());
    }

    @Test
    public void activePowerAfterBuildTest(){
        playerHephaestus.activePowerAfterBuild();
        assertTrue(playerHephaestus.getActivePower());
    }

    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(buildPosition)));

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));
        assertTrue(playerHephaestus.canUsePower(power, board.getAdjacentPlayers(workerPosition)));

        List<Cell> power2 = new ArrayList<>();
        power2.add(board.getCell(otherPosition));
        assertFalse(playerHephaestus.canUsePower(power2,board.getAdjacentPlayers(workerPosition)));

        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(otherPosition)));
    }

    @Test
    public void usePowerTest(){
        boardChange = playerHephaestus.usePower(board.getCell(buildPosition));
        assertFalse(playerHephaestus.getActivePower());
        assertEquals(BuildType.LEVEL,boardChange.getBuildType());

    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerHephaestus.getPowerListDimension());
    }

    @Test
    public void notUsedPowerTest(){
        board.putWorker(workerPosition, PlayerIndex.PLAYER0);
        playerHephaestus.setWorkerSituation(board.getCell(otherPosition), board.getCell(workerPosition), false);
        playerHephaestus.activePowerAfterBuild();
        assertTrue(playerHephaestus.getActivePower());

        playerHephaestus.hasWin();
        assertFalse(playerHephaestus.getActivePower());
    }

}