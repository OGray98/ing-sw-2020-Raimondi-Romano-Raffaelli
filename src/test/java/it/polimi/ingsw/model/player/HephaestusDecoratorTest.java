package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.HephaestusDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HephaestusDecoratorTest {


    private static Deck deck;
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
        deck = new Deck();
        cardHephaestus = deck.getGodCard("Hephaestus");
        playerHephaestus = cardHephaestus.setPlayer(new Player("jack", PlayerIndex.PLAYER0));
        workerPosition = new Position(1,1);
        buildPosition = new Position(1,2);
        otherPosition = new Position(1,0);
    }

    /*Check if the card is no more available because it has been chosen*/
    @Test
    public void setChosenGodTest(){
        cardHephaestus.setChosenGod(true);
        assertTrue(cardHephaestus.getBoolChosenGod());
    }



    /*Check if canUsePower is correct in different cases*/
    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(buildPosition)));
        board.constructBlock(buildPosition);

        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));
        assertTrue(playerHephaestus.canUsePower(power, board.getAdjacentPlayers(workerPosition)));

        List<Cell> power2 = new ArrayList<>();
        power2.add(board.getCell(otherPosition));
        assertFalse(playerHephaestus.canUsePower(power2,board.getAdjacentPlayers(workerPosition)));

        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(otherPosition)));
    }

    /*Check that canUsePower() returns false if the building has a level 3 tower before using the power*/
    @Test
    public void canUsePowerBlocked(){
        //Create a tower level 2
        board.constructBlock(buildPosition);
        board.putWorker(workerPosition, PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition), false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition), board.getCell(buildPosition)));

        board.constructBlock(buildPosition);
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));

        assertTrue(playerHephaestus.canUsePower(power, board.getAdjacentPlayers(workerPosition)));

        //Upgrade tower to level 3
        board.constructBlock(buildPosition);

        List<Cell> power2 = new ArrayList<>();
        power2.add(board.getCell(buildPosition));
        assertFalse(playerHephaestus.canUsePower(power2, board.getAdjacentPlayers(workerPosition)));
    }

    /*Check usePower() exceptions and correctness*/
    @Test
    public void usePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerHephaestus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        assertTrue(playerHephaestus.canBuild(board.getAdjacentPlayers(workerPosition),board.getCell(buildPosition)));
        board.constructBlock(buildPosition);
        assertEquals(1,board.getCell(buildPosition).getLevel());
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(buildPosition));
        assertTrue(playerHephaestus.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        boardChange = playerHephaestus.usePower(board.getCell(buildPosition));
        board.updateAfterPower(boardChange);
        assertEquals(BuildType.LEVEL,boardChange.getBuildType());
        assertEquals(2,board.getCell(buildPosition).getLevel());

    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerHephaestus.getPowerListDimension());
    }



    @After
    public void afterAll(){

        board = null;
        deck = null;
        cardHephaestus = null;
        playerHephaestus = null;
        workerPosition = null;
        buildPosition = null;
        otherPosition = null;

    }

}