package org.example;

import org.junit.Before;
import org.junit.Test;

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
        playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(buildPosition));
        assertTrue(playerHephaestus.canUsePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),board.getCell(buildPosition)));
        assertFalse(playerHephaestus.canUsePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),board.getCell(otherPosition)));
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(otherPosition)));
    }

    @Test
    public void usePowerTest(){
        boardChange = playerHephaestus.usePower(board.getAdjacentCells(workerPosition),board.getAdjacentPlayers(workerPosition),board.getCell(buildPosition));
        assertFalse(playerHephaestus.getActivePower());
        assertEquals(BuildType.LEVEL,boardChange.getBuildType());

    }




}