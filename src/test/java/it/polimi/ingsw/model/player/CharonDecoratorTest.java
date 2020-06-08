package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CharonDecoratorTest {

    private static Deck deck;
    CardInterface cardCharon;
    PlayerInterface playerCharon;
    PlayerInterface playerOpponent;
    private static Board board;
    private static Position workerOnePos;
    private static Position workerTwoPos;
    private static Position workerOpponentPos1;
    private static Position workerOpponentPos2;
    PlayerInterface otherPlayerOpponent;

    @Before
    public void init(){
        deck = new Deck();
        board = new Board();
        cardCharon = deck.getGodCard("Charon");
        playerCharon = cardCharon.setPlayer(new Player("Jack", PlayerIndex.PLAYER0));
        playerOpponent = new Player("Bob", PlayerIndex.PLAYER1);
        workerOnePos = new Position(1,1);
        workerTwoPos = new Position(2,2);
        //workerOne can use power, if the Cell (1,0) is free
        workerOpponentPos1 = new Position(1,2);
        //workerOne cant use power
        workerOpponentPos2 = new Position(0,0);
        otherPlayerOpponent = new Player("John", PlayerIndex.PLAYER2);
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(2,playerCharon.getPowerListDimension());
    }

    @Test
    public void setChosenGodTest() {
        cardCharon.setChosenGod(true);
        assertTrue(cardCharon.getBoolChosenGod());
    }

    /*Check return value of canUsePower() in some particular cases*/
    @Test
    public void canUsePowerTest(){
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        board.putWorker(workerTwoPos, PlayerIndex.PLAYER0);
        playerCharon.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        //Case 1 : charon cant use power on a distant cell
        List<Cell> case1 = new ArrayList<>();
        case1.add(board.getCell(workerOnePos));
        case1.add(board.getCell(new Position(3,3)));

        assertFalse(playerCharon.canUsePower(case1, board.getAdjacentPlayers(workerOnePos)));

        //Case 2 : charon cant use power on an empty cell
        List<Cell> case2 = new ArrayList<>();
        case2.add(board.getCell(new Position(0,1)));

        assertFalse(playerCharon.canUsePower(case2, board.getAdjacentPlayers(workerOnePos)));

        //Case 3 : charon cant use power on a cell occupied by his other worker
        List<Cell> case3 = new ArrayList<>();
        //case3.add(board.getCell(workerOnePos));
        case3.add(board.getCell(workerTwoPos));

        assertFalse(playerCharon.canUsePower(case3, board.getAdjacentPlayers(workerOnePos)));

        //Case 4 : charon can use power on an adjacent enemy worker
        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        List<Cell> case4 = new ArrayList<>();
        case4.add(board.getCell(workerOpponentPos1));
        case4.add(board.getCell(new Position(1,0)));

        Map<Position, PlayerIndex> case4p = new HashMap<>();
        case4p.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertFalse(playerCharon.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertTrue(playerCharon.canUsePower(case4, case4p));
    }

    @Test
    public void canUsePowerOnDomeTest(){
        //place charon workers
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        board.putWorker(workerTwoPos, PlayerIndex.PLAYER0);
        playerCharon.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        //place enemy workers
        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        // Charon cant use power if the destination cell has dome
        Position domeCellPos = new Position(1,0);

        board.constructBlock(domeCellPos);
        board.constructBlock(domeCellPos);
        board.constructBlock(domeCellPos);
        board.constructBlock(domeCellPos);

        List<Cell> caseDome = new ArrayList<>();
        caseDome.add(board.getCell(workerOpponentPos1));
        caseDome.add(board.getCell(new Position(1,0)));

        Map<Position, PlayerIndex> caseDomep = new HashMap<>();
        caseDomep.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertFalse(playerCharon.canUsePower(caseDome, caseDomep));
    }

    @Test
    public void canUsePowerOnOccupiedCell(){
        //place charon workers
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        board.putWorker(workerTwoPos, PlayerIndex.PLAYER0);
        playerCharon.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        //place enemy workers
        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        board.putWorker(new Position(1,0),PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        // charon cannot use power when the destination cell is occupied by a player
        List<Cell> caseOccupied = new ArrayList<>();
        caseOccupied.add(board.getCell(workerOpponentPos1));
        caseOccupied.add(board.getCell(new Position(1,0)));

        Map<Position, PlayerIndex> caseOccupiedp = new HashMap<>();
        caseOccupiedp.put(workerOpponentPos1, PlayerIndex.PLAYER1);
        caseOccupiedp.put(new Position(1,0), PlayerIndex.PLAYER1);

        assertFalse(playerCharon.canUsePower(caseOccupied, caseOccupiedp));
    }

    @Test
    public void canUsePowerWrongArguments(){

        List<Cell> caseWrong = new ArrayList<>();
        caseWrong.add(board.getCell(workerOpponentPos1));
        caseWrong.add(board.getCell(new Position(1,0)));

        Map<Position, PlayerIndex> caseWrongp = new HashMap<>();
        caseWrongp.put(workerOpponentPos1, PlayerIndex.PLAYER1);
        caseWrongp.put(new Position(1,0), PlayerIndex.PLAYER1);
        caseWrongp.put(new Position(3,4), PlayerIndex.PLAYER1);

        assertFalse(playerCharon.canUsePower(caseWrong, caseWrongp));
    }

    @Test
    public void usePowerTest(){
        //place a charon worker
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        playerCharon.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        //place an enemy worker
        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        //normal power use
        List<Cell> caseNormal = new ArrayList<>();
        caseNormal.add(board.getCell(workerOpponentPos1));
        caseNormal.add(board.getCell(new Position(1,0)));

        Map<Position, PlayerIndex> caseNormalp = new HashMap<>();
        caseNormalp.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertTrue(playerCharon.canUsePower(caseNormal, caseNormalp));

        BoardChange powerResult = playerCharon.usePower(board.getCell(new Position(1,2)));

        assertEquals(powerResult.getChanges().size(), 1);
        assertTrue(powerResult.getChanges().values().contains(PlayerIndex.PLAYER1));

        //Check changes on board after the power
        board.updateAfterPower(powerResult);

        assertEquals(board.getOccupiedPlayer(workerOnePos), PlayerIndex.PLAYER0);
        assertEquals(board.getOccupiedPlayer(new Position(1,0)), PlayerIndex.PLAYER1);
    }

    @Test
    public void getSecondPowerPosition(){
        playerCharon.setStartingWorkerSituation(board.getCell(new Position(0,1)),false);

        assertEquals(playerCharon.getSecondPowerPosition(new Position(0,2)), new Position(0,0));

        try{
            playerCharon.getSecondPowerPosition(new Position(1,1));
        }
        catch (InvalidPositionException e){
            assertEquals(e.getMessage(), "You cannot have a position in : [" + -1 + "][" + 1 + "]");
        }

    }

    @After
    public void afterAll(){

        deck = null;
        board = null;
        cardCharon = null;
        playerCharon = null;
        playerOpponent = null;
        workerOnePos = null;
        workerOpponentPos1 = null;
        workerOpponentPos2 = null;
        otherPlayerOpponent = null;

    }
}
