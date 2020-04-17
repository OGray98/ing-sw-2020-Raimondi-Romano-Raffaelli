package it.polimi.ingsw;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class MinotaurDecoratorTest {

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
        board = new Board();
        cardMinotaur = new MinotaurDecorator();
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

    @Test
    public void canMoveMinotaurTest(){
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        board.putWorker(workerTwoPos, PlayerIndex.PLAYER0);
        playerMinotaur.setStartingWorkerSituation(board.getCell(workerOnePos),false);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(new Position(3,3))));
        assertFalse(playerMinotaur.getActivePower());
        assertTrue(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),new Cell(0,1)));
        assertFalse(playerMinotaur.getActivePower());
        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerTwoPos)));
        assertFalse(playerMinotaur.getActivePower());

        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertTrue(playerMinotaur.getActivePower());

        board.putWorker(workerOpponentPos2,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos2),false);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertTrue(playerMinotaur.getActivePower());

        board.constructBlock(workerOpponentPos1);
        board.constructBlock(workerOpponentPos1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);
        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertFalse(playerMinotaur.getActivePower());

        board.constructBlock(workerOnePos);
        board.constructBlock(workerOnePos);
        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos), board.getCell(workerOpponentPos2)));
        assertTrue(playerMinotaur.getActivePower());
        assertTrue(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos), board.getCell(new Position(0,1))));
    }

    @Test
    public void canUsePowerTest(){
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

        //Test the case of normal power use with free cell
        List<Cell> normalUse = new ArrayList<>();
        Map<Position, PlayerIndex> normalUsePlayer = new HashMap<>();

        normalUse.add(new Cell(workerOpponentPos1.row, workerOpponentPos1.col));
        normalUse.add(new Cell(workerOpponentPos1.row, workerOpponentPos2.col + 1));

        normalUsePlayer.put(workerOpponentPos1, PlayerIndex.PLAYER1);

        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos), board.getCell(workerOpponentPos1)));
        assertTrue(playerMinotaur.getActivePower());

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

    @Test
    public void twoCanMoveTest(){
        /*if player refuse to use power, active power must become false*/
        board.putWorker(workerOnePos,PlayerIndex.PLAYER0);
        playerMinotaur.setStartingWorkerSituation(board.getCell(workerOnePos),false);
        board.constructBlock(workerOpponentPos1);
        board.putWorker(workerOpponentPos1,PlayerIndex.PLAYER2);
        otherPlayerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPos1),false);


        assertFalse(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(workerOpponentPos1)));
        assertTrue(playerMinotaur.getActivePower());

        assertTrue(playerMinotaur.canMove(board.getAdjacentPlayers(workerOnePos),board.getCell(new Position(0,1))));
        assertFalse(playerMinotaur.getActivePower());
    }
}
