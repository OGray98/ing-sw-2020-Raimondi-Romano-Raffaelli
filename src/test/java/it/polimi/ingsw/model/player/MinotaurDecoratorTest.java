package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.MinotaurDecorator;
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

public class MinotaurDecoratorTest {

    private static Deck deck;
    CardInterface cardMinotaur;
    PlayerInterface playerMinotaur;
    PlayerInterface playerOpponent;
    private static Board board;
    private static Position workerOnePos;
    private static Position workerTwoPos;
    private static Position workerOpponentPos1;
    private static Position workerOpponentPos2;
    PlayerInterface otherPlayerOpponent;

    @Before
    public void init(){
        deck = new Deck(3);
        board = new Board();
        cardMinotaur = deck.getGodCard("Minotaur");
        playerMinotaur = cardMinotaur.setPlayer(new Player("Jack", PlayerIndex.PLAYER0));
        playerOpponent = new Player("Bob", PlayerIndex.PLAYER1);
        workerOnePos = new Position(1,1);
        workerTwoPos = new Position(2,2);
        //workerOne can use power, if the Cell (1,3) is free
        workerOpponentPos1 = new Position(1,2);
        //workerOne cant use power
        workerOpponentPos2 = new Position(0,0);
        otherPlayerOpponent = new Player("John", PlayerIndex.PLAYER2);
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(2,playerMinotaur.getPowerListDimension());
    }

    /*Check return value of canUsePower() in some particular cases*/
    @Test
    public void canUsePowerTestPart1(){
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        board.putWorker(workerTwoPos, PlayerIndex.PLAYER0);
        playerMinotaur.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        //Case 1 : minotaur cant use power on a distant cell
        List<Cell> case1 = new ArrayList<>();
        case1.add(board.getCell(workerOnePos));
        case1.add(board.getCell(new Position(3,3)));

        assertFalse(playerMinotaur.canUsePower(case1, board.getAdjacentPlayers(workerOnePos)));

        //Case 2 : minotaur cant use power on an empty cell
        List<Cell> case2 = new ArrayList<>();
        case2.add(board.getCell(new Position(0,1)));

        assertFalse(playerMinotaur.canUsePower(case2, board.getAdjacentPlayers(workerOnePos)));

        //Case 3 : minotaur cant use power on a cell occupied by his other worker
        List<Cell> case3 = new ArrayList<>();
        //case3.add(board.getCell(workerOnePos));
        case3.add(board.getCell(workerTwoPos));

        assertFalse(playerMinotaur.canUsePower(case3, board.getAdjacentPlayers(workerOnePos)));

        //Case 4 : minotaur can use power on an adjacent enemy worker
        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        List<Cell> case4 = new ArrayList<>();
        case4.add(board.getCell(workerOpponentPos1));
        case4.add(board.getCell(new Position(1,3)));

        Map<Position, PlayerIndex> case4p = new HashMap<>();
        case4p.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertTrue(playerMinotaur.canUsePower(case4, case4p));

        //Case 5 : minotaur cant use power if the enemy player is at the border of the map
        board.putWorker(workerOpponentPos2,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos2),false);

        List<Cell> case5 = new ArrayList<>();
        case5.add(board.getCell(workerOpponentPos2));

        Map<Position, PlayerIndex> case5p = new HashMap<>();
        case5p.put(workerOpponentPos2, PlayerIndex.PLAYER1);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertFalse(playerMinotaur.canUsePower(case5, case5p));

        //Case 6 : minotaur cant use power on an enemy if the enemy is 2+ level up
        board.constructBlock(workerOpponentPos1);
        board.constructBlock(workerOpponentPos1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        List<Cell> case6 = new ArrayList<>();
        case6.add(board.getCell(workerOpponentPos1));
        case6.add(board.getCell(new Position(1,3)));

        Map<Position, PlayerIndex> case6p = new HashMap<>();
        case6p.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertFalse(playerMinotaur.canUsePower(case6, case6p));
    }

    /*Check exceptions in canUsePower and the return value of canUsePower() in other particular cases*/
    @Test
    public void canUsePowerTestPart2(){
        //Put workers on the board
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        playerMinotaur.setStartingWorkerSituation(board.getCell(workerOnePos),false);
        board.putWorker(workerOpponentPos1, PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1), false);

        //This is the case when the cell of the opponent after the power would be over the board
        List<Cell> cellsNeeded = new ArrayList<>();

        cellsNeeded.add(new Cell(workerOpponentPos2.row, workerOpponentPos2.col));

        assertFalse(playerMinotaur.canUsePower(cellsNeeded, board.getAdjacentPlayers(workerOnePos)));

        //IllegalArgumentException for map check:
        try{
            playerMinotaur.canUsePower(cellsNeeded, board.getAdjacentPlayers(workerOnePos));
        }
        catch (IllegalArgumentException e){
            assertEquals("map passed has an illegal size", e.getMessage());
        }

        //IllegalArgumentException check:
        cellsNeeded.add(new Cell(4,4));
        cellsNeeded.add(new Cell(3,4));
        try{
            playerMinotaur.canUsePower(cellsNeeded, board.getAdjacentPlayers(workerOnePos));
        }
        catch(IllegalArgumentException e){
            assertEquals("list passed has an illegal size!", e.getMessage());
        }

        //Case 7 : minotaur can use power on enemy cell when he is 2+ level up the enemy
        board.constructBlock(workerOnePos);
        board.constructBlock(workerOnePos);

        List<Cell> normalUse = new ArrayList<>();
        Map<Position, PlayerIndex> normalUsePlayer = new HashMap<>();

        normalUse.add(new Cell(workerOpponentPos1.row, workerOpponentPos1.col));
        normalUse.add(new Cell(workerOpponentPos1.row, workerOpponentPos2.col + 1));

        normalUsePlayer.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertTrue(playerMinotaur.canUsePower(normalUse, normalUsePlayer));

        //normal use of power but the cell where the opponent player would be moved is occupied by a worker
        Position pos13 = new Position(workerOpponentPos1.row, workerOpponentPos2.col + 1);
        board.putWorker(pos13, PlayerIndex.PLAYER1);
        normalUsePlayer.put(pos13, PlayerIndex.PLAYER1);

        assertFalse(playerMinotaur.canUsePower(normalUse, normalUsePlayer));

        //normale case but the cell where the opponent player would be moved is occupied by a dome
        List<Cell> normalUse2 = new ArrayList<>();
        Map<Position, PlayerIndex> normalUsePlayer2 = new HashMap<>();

        Position otherOpponentPos = new Position(2,1);

        board.putWorker(otherOpponentPos, PlayerIndex.PLAYER2);

        normalUse2.add(new Cell(otherOpponentPos.row, otherOpponentPos.col));
        normalUse2.add(new Cell(otherOpponentPos.row + 1, otherOpponentPos.col));

        normalUsePlayer2.put(otherOpponentPos, PlayerIndex.PLAYER2);

        assertTrue(playerMinotaur.canUsePower(normalUse2, normalUsePlayer2));

        Position domeCellPos = new Position(3,1);

        board.constructBlock(domeCellPos);
        board.constructBlock(domeCellPos);
        board.constructBlock(domeCellPos);
        board.constructBlock(domeCellPos);

        assertTrue(board.getCell(domeCellPos).hasDome());

        List<Cell> normalUse3 = new ArrayList<>();

        normalUse3.add(board.getCell(domeCellPos));
        normalUse3.add(board.getCell(otherOpponentPos));

        assertFalse(playerMinotaur.canUsePower(normalUse3, normalUsePlayer2));
    }

    @Test
    public void usePowerTest(){
        board.putWorker(workerOnePos, PlayerIndex.PLAYER0);
        board.putWorker(workerOpponentPos1, PlayerIndex.PLAYER1);
        playerMinotaur.setStartingWorkerSituation(board.getCell(workerOnePos), false);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1), false);

        List<Cell> normalUse = new ArrayList<>();
        Map<Position, PlayerIndex> normalUsePlayer = new HashMap<>();

        normalUse.add(new Cell(workerOpponentPos1.row, workerOpponentPos1.col + 1));
        normalUse.add(new Cell(workerOpponentPos1.row, workerOpponentPos1.col));

        normalUsePlayer.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertTrue(playerMinotaur.canUsePower(normalUse, normalUsePlayer));

        BoardChange powerRes = playerMinotaur.usePower(board.getCell(workerOpponentPos1));

        assertEquals(powerRes.getChanges().size(), 2);
        assertTrue(powerRes.getChanges().values().contains(PlayerIndex.PLAYER0));
        assertTrue(powerRes.getChanges().values().contains(PlayerIndex.PLAYER1));

        board.updateAfterPower(powerRes);

        assertEquals(board.getOccupiedPlayer(workerOpponentPos1), PlayerIndex.PLAYER0);
        assertEquals(board.getOccupiedPlayer(new Position(1,3)), PlayerIndex.PLAYER1);
    }

    /*Check that the player wins if he uses power to move up*/
    @Test
    public void hasWinAfterPower(){

        board.constructBlock(workerOnePos);
        board.constructBlock(workerOnePos);

        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        playerMinotaur.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        board.constructBlock(workerOpponentPos1);
        board.constructBlock(workerOpponentPos1);
        board.constructBlock(workerOpponentPos1);

        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        List<Cell> case4 = new ArrayList<>();
        case4.add(board.getCell(workerOpponentPos1));
        case4.add(board.getCell(new Position(1,3)));

        Map<Position, PlayerIndex> case4p = new HashMap<>();
        case4p.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertTrue(playerMinotaur.canUsePower(case4, case4p));
        BoardChange powerRes = playerMinotaur.usePower(board.getCell(workerOpponentPos1));
        board.updateAfterPower(powerRes);
        assertEquals(board.getOccupiedPlayer(workerOpponentPos1), PlayerIndex.PLAYER0);
        assertTrue(playerMinotaur.hasWin());
    }
}
