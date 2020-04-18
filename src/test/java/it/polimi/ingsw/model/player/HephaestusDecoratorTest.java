package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.player.HephaestusDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
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
        //cardHephaestus = new HephaestusDecorator();
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
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(buildPosition)));
        board.constructBlock(buildPosition);
        assertEquals(1,board.getCell(buildPosition).getLevel());
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));
        playerHephaestus.activePowerAfterBuild();
        assertTrue(playerHephaestus.getActivePower());
        assertTrue(playerHephaestus.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        boardChange = playerHephaestus.usePower(board.getCell(buildPosition));
        board.updateAfterPower(boardChange);
        assertFalse(playerHephaestus.getActivePower());
        assertEquals(BuildType.LEVEL,boardChange.getBuildType());
        assertEquals(2,board.getCell(buildPosition).getLevel());


        assertFalse(playerHephaestus.getActivePower());
    }

    @Test
    public void notUsePowerTestPart(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(buildPosition)));
        board.constructBlock(buildPosition);
        assertEquals(1,board.getCell(buildPosition).getLevel());
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));
        playerHephaestus.activePowerAfterBuild();
        assertTrue(playerHephaestus.getActivePower());


        // start another Hephaestus turn
        assertTrue(playerHephaestus.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(otherPosition)));
        playerHephaestus.move(board.getCell(otherPosition));
        playerHephaestus.setWorkerSituation(board.getCell(workerPosition),board.getCell(otherPosition),false);
        assertFalse(playerHephaestus.getActivePower());
    }

    @Test
    public void notUsePowerTestPart2(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(buildPosition)));
        board.constructBlock(buildPosition);
        assertEquals(1,board.getCell(buildPosition).getLevel());
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));
        playerHephaestus.activePowerAfterBuild();
        assertTrue(playerHephaestus.getActivePower());

        // start another Hephaestus turn
        board.putWorker(new Position(3,4),PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(new Position(3,4)),false);
        assertFalse(playerHephaestus.getActivePower());
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerHephaestus.getPowerListDimension());
    }



}