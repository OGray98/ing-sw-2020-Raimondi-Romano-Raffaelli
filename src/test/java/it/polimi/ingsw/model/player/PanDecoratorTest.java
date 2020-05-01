package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.PanDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PanDecoratorTest {

    private static Deck deck;
    private static CardInterface cardPan;
    private static PlayerInterface playerPan;
    private static Board board;
    private static Position workerPosition;
    private static Position buildPositionLevel1;
    private static Position buildPositionLevel2;
    private static Position buildPositionLevel3;

    @Before
    public void init(){
        deck = new Deck();
        cardPan = deck.getGodCard("Pan");
        board = new Board();
        playerPan = cardPan.setPlayer(new Player("jack", PlayerIndex.PLAYER0));
        workerPosition = new Position(1,1);
        buildPositionLevel1 = new Position(1,2);
        buildPositionLevel2 = new Position(2,2);
        buildPositionLevel3 = new Position(2,1);

    }

    @Test
    public void setChosenGodTest(){
        cardPan.setChosenGod(true);
        assertTrue(cardPan.getBoolChosenGod());
    }


    @Test
    public void hasWinTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPan.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.constructBlock(buildPositionLevel1);
        board.changeWorkerPosition(workerPosition,buildPositionLevel1);
        playerPan.setWorkerSituation(board.getCell(workerPosition),board.getCell(buildPositionLevel1),false);
        board.changeWorkerPosition(buildPositionLevel1,workerPosition);// 1 -> 0
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(workerPosition),false);
        assertFalse(playerPan.hasWin());
        board.constructBlock(buildPositionLevel2);
        board.constructBlock(buildPositionLevel2);
        board.changeWorkerPosition(workerPosition,buildPositionLevel1);
        playerPan.setWorkerSituation(board.getCell(workerPosition),board.getCell(buildPositionLevel1),false);
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel1);// 2 -> 1
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel1),false);
        assertFalse(playerPan.hasWin());
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,workerPosition);// 2 -> 0
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(workerPosition),false);
        assertTrue(playerPan.hasWin());
        board.constructBlock(buildPositionLevel3);
        board.constructBlock(buildPositionLevel3);
        board.constructBlock(buildPositionLevel3);
        board.changeWorkerPosition(workerPosition,buildPositionLevel1);
        playerPan.setWorkerSituation(board.getCell(workerPosition),board.getCell(buildPositionLevel1),false);
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel3);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel3),false);
        board.changeWorkerPosition(buildPositionLevel3,buildPositionLevel2);// 3 -> 2
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel3),board.getCell(buildPositionLevel2),false);
        assertFalse(playerPan.hasWin());
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel3);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel3),false);
        board.changeWorkerPosition(buildPositionLevel3,buildPositionLevel1);// 3 -> 1
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel3),board.getCell(buildPositionLevel1),false);
        assertTrue(playerPan.hasWin());
        board.changeWorkerPosition(buildPositionLevel1,buildPositionLevel2);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel1),board.getCell(buildPositionLevel2),false);
        board.changeWorkerPosition(buildPositionLevel2,buildPositionLevel3);
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel2),board.getCell(buildPositionLevel3),false);
        board.changeWorkerPosition(buildPositionLevel3,workerPosition); // 3 -> 0
        playerPan.setWorkerSituation(board.getCell(buildPositionLevel3),board.getCell(workerPosition),false);
        assertTrue(playerPan.hasWin());


    }

    /*Check that canMove() for Pan does not return true in the cells where hi power is active*/
    @Test
    public void canMoveTest(){
        Position lvl1 = new Position(1,2);
        Position lvl2 = new Position(1,3);
        Position lvl3 = new Position(1,4);

        Position movelvl0 = new Position(0,2);
        Position movelvl1 = new Position(0,3);
        Position movelvl2 = new Position(0,4);

        board.constructBlock(lvl1);
        board.constructBlock(lvl2);
        board.constructBlock(lvl2);
        board.constructBlock(lvl3);
        board.constructBlock(lvl3);
        board.constructBlock(lvl3);

        board.constructBlock(movelvl1);
        board.constructBlock(movelvl2);
        board.constructBlock(movelvl2);

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPan.setStartingWorkerSituation(board.getCell(workerPosition),false);

        Map<Position, PlayerIndex> emptyMap = new HashMap<>();

        board.changeWorkerPosition(workerPosition, lvl1);
        playerPan.move(board.getCell(lvl1));
        assertTrue(playerPan.canMove(emptyMap, board.getCell(movelvl0)));//1 -> 0

        board.changeWorkerPosition(lvl1, lvl2);
        playerPan.move(board.getCell(lvl2));
        assertFalse(playerPan.canMove(emptyMap, board.getCell(movelvl0)));//2 -> 0
        assertTrue(playerPan.canMove(emptyMap, board.getCell(movelvl1)));//2 -> 1

        board.changeWorkerPosition(lvl2, lvl3);
        playerPan.move(board.getCell(lvl3));
        assertFalse(playerPan.canMove(emptyMap, board.getCell(movelvl0)));//3 -> 0
        assertFalse(playerPan.canMove(emptyMap, board.getCell(movelvl1)));//3 -> 1
        assertTrue(playerPan.canMove(emptyMap, board.getCell(movelvl2)));//3 -> 2
    }

    @Test
    public void canUsePowerTest(){
        Position lvl1 = new Position(1,2);
        Position lvl2 = new Position(1,3);
        Position lvl3 = new Position(1,4);

        Position movelvl0 = new Position(0,2);
        Position movelvl1 = new Position(0,3);
        Position movelvl2 = new Position(0,4);

        board.constructBlock(lvl1);
        board.constructBlock(lvl2);
        board.constructBlock(lvl2);
        board.constructBlock(lvl3);
        board.constructBlock(lvl3);
        board.constructBlock(lvl3);

        board.constructBlock(movelvl1);
        board.constructBlock(movelvl2);
        board.constructBlock(movelvl2);

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPan.setStartingWorkerSituation(board.getCell(workerPosition),false);

        Map<Position, PlayerIndex> emptyMap = new HashMap<>();

        board.changeWorkerPosition(workerPosition, lvl1);
        playerPan.move(board.getCell(lvl1));
        List<Cell> case1 = new ArrayList<>();
        case1.add(board.getCell(movelvl0));
        assertFalse(playerPan.canUsePower(case1, emptyMap));//1 -> 0

        board.changeWorkerPosition(lvl1, lvl2);
        playerPan.move(board.getCell(lvl2));
        assertTrue(playerPan.canUsePower(case1, emptyMap));//2 -> 0
        List<Cell> case2 = new ArrayList<>();
        case2.add(board.getCell(movelvl1));
        assertFalse(playerPan.canUsePower(case2, emptyMap));//2 -> 1

        board.changeWorkerPosition(lvl2, lvl3);
        playerPan.move(board.getCell(lvl3));
        List<Cell> case4 = new ArrayList<>();
        case4.add(board.getCell(new Position(2,3)));
        assertTrue(playerPan.canUsePower(case4, emptyMap));//3 -> 0
        assertTrue(playerPan.canUsePower(case2, emptyMap));//3 -> 1
        List<Cell> case3 = new ArrayList<>();
        case3.add(board.getCell(movelvl2));
        assertFalse(playerPan.canUsePower(case3, emptyMap));//3 -> 2
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(0,playerPan.getPowerListDimension());
    }



    @After
    public void afterAll(){

        deck = null;
        playerPan = null;
        cardPan = null;
        board = null;
        workerPosition = null;
        buildPositionLevel1 = null;
        buildPositionLevel2 = null;
        buildPositionLevel3 = null;
    }
}