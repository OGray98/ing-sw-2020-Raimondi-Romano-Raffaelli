package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.AthenaDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AthenaDecoratorTest {

    private static CardInterface cardAthena;
    private static PlayerInterface playerAthena;
    private static Board board;
    private static Position workerPos;
    private static Position lvlUpPos;
    private static Position notLvlUpPos;
    private static Deck deck;

    @Before
    public void init(){
        board = new Board();
        deck = new Deck();
        cardAthena = deck.getGodCard("Athena");
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
    public void getPowerListDimensionTest(){
        assertEquals(1,playerAthena.getPowerListDimension());
    }

    @Test
    public void canMoveTest(){

        board.putWorker(workerPos,PlayerIndex.PLAYER0);
        playerAthena.setStartingWorkerSituation(board.getCell(workerPos), false);
        //Verify if Athena worker want to level up he can't
        assertFalse(playerAthena.canMove(board.getAdjacentPlayers(workerPos),board.getCell(lvlUpPos)));
    }

    @Test
    public void canUsePowerAthenaTest(){

        //Verify if Athena worker up to one level and activate his power
        board.putWorker(workerPos,PlayerIndex.PLAYER0);
        playerAthena.setStartingWorkerSituation(board.getCell(workerPos),false);
        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(lvlUpPos));
        assertTrue(playerAthena.canUsePower(powers,board.getAdjacentPlayers(workerPos)));
        List<Cell> powers1 = new ArrayList<>();
        powers1.add(board.getCell(new Position(3,2)));
        //Verify if Athena worker does not want to level up
        assertFalse(playerAthena.canUsePower(powers1,board.getAdjacentPlayers(workerPos)));
    }



    @Test
    public void usePowerAthenaTest(){
        //Verify if Athena power works
        board.putWorker(workerPos, PlayerIndex.PLAYER0);
        playerAthena.setStartingWorkerSituation(board.getCell(workerPos), false);

        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(lvlUpPos));
        assertTrue(playerAthena.canUsePower(powers,board.getAdjacentPlayers(workerPos)));
        BoardChange powerResult = playerAthena.usePower(board.getCell(lvlUpPos));
        board.updateAfterPower(powerResult);

        assertEquals(PlayerIndex.PLAYER0,board.getOccupiedPlayer(lvlUpPos));


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




    }

    @Test
    public void hasWinAfterPowerTest(){
        board.constructBlock(workerPos);
        board.constructBlock(workerPos);

        board.putWorker(workerPos, PlayerIndex.PLAYER0);
        playerAthena.setStartingWorkerSituation(board.getCell(workerPos), false);

        board.constructBlock(lvlUpPos);
        board.constructBlock(lvlUpPos);

        List<Cell> list = new ArrayList<>();
        Map<Position, PlayerIndex> plist = new HashMap<>();

        list.add(board.getCell(lvlUpPos));

        assertTrue(playerAthena.canUsePower(list, plist));

        BoardChange powerRes = playerAthena.usePower(board.getCell(lvlUpPos));

        board.updateAfterPower(powerRes);
        assertTrue(playerAthena.hasWin());
    }
}
