package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.NotAdjacentBuildingException;
import it.polimi.ingsw.exception.NotAdjacentMovementException;
import it.polimi.ingsw.exception.NotPresentWorkerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TurnManagerTest {

    private static TurnManager turnManager;
    private static Game gameInstance;
    private static Board board;
    private static List<PlayerInterface> players;
    private static List<Position> domePos;
    private static List<Position> level1Pos;
    private static List<Position> level2Pos;
    private static List<Position> level3Pos;
    private static List<String> gods;
    private static List<Position> workerPos;

    @Before
    public void setUp() {
        board = new Board();

        players = new ArrayList<>(3);
        players.add(new Player("Jack", PlayerIndex.PLAYER0));
        players.add(new Player("Rock", PlayerIndex.PLAYER1));
        players.add(new Player("Creed", PlayerIndex.PLAYER2));

        gameInstance = new Game(3);
        players.forEach(playerInterface -> gameInstance.addPlayer(playerInterface.getPlayerNum(), playerInterface.getNickname()));

        gods = new ArrayList<>(List.of("Atlas", "Demeter", "Prometheus"));
        Collections.rotate(gods, -1);
        gameInstance.setGodsChosenByGodLike(gods);
        for (String god : gods) gameInstance.setPlayerCard(god);
        Collections.rotate(gods, 1);
        gameInstance.chooseFirstPlayer(PlayerIndex.PLAYER0);

        turnManager = new TurnManager(gameInstance);
    }

    @Test
    public void constructTest() {
        try {
            new TurnManager(null);
        } catch (NullPointerException e) {
            assertEquals("gameInstance", e.getMessage());
        }
    }

    @Test
    public void moveWorkerTest() {
        /*
            0A 2  0B 2  0A
            3  3D 3D 2  0
            1B 0C 0  0  0
            3  3  0  0  0
            0  0  0  0  0C
         */
        List<Position> workerPos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 4),
                new Position(0, 2),
                new Position(2, 0),
                new Position(2, 1),
                new Position(4, 4)
        ));

        List<Position> buildPos = new ArrayList<>(List.of(
                new Position(0, 1),
                new Position(0, 1),
                new Position(0, 3),
                new Position(0, 3),
                new Position(1, 0),
                new Position(1, 0),
                new Position(1, 0),
                new Position(1, 3),
                new Position(1, 3),
                new Position(2, 0),
                new Position(3, 0),
                new Position(3, 0),
                new Position(3, 0),
                new Position(3, 1),
                new Position(3, 1),
                new Position(3, 1)
        ));

        List<Position> domePos = new ArrayList<>(List.of(
                new Position(1, 1),
                new Position(1, 1),
                new Position(1, 1),
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 2),
                new Position(1, 2),
                new Position(1, 2)
        ));

        buildPos.forEach(pos -> gameInstance.build(pos));
        domePos.forEach(pos -> gameInstance.build(pos));

        workerPos.forEach(pos -> gameInstance.putWorker(pos));


        turnManager.startTurn();
        assertTrue(turnManager.canCurrentPlayerMoveAWorker());
        List<Position> movementPos = new ArrayList<>();
        assertEquals(movementPos, turnManager.movementPositions(workerPos.get(0)));
        movementPos.add(new Position(1, 4));
        assertEquals(movementPos, turnManager.movementPositions(workerPos.get(1)));

        try {
            turnManager.movementPositions(null);
        } catch (NullPointerException e) {
            assertEquals("workerPos", e.getMessage());
        }

        try {
            turnManager.movementPositions(workerPos.get(2));
        } catch (NotPresentWorkerException e) {
            assertEquals("There isn't a worker of " + PlayerIndex.PLAYER0 +
                    " in : [" + workerPos.get(2).row + "][" + workerPos.get(2).col + "]", e.getMessage());
        }

        assertTrue(turnManager.isValidMovement(workerPos.get(1), movementPos.get(0)));
        assertFalse(turnManager.isValidMovement(workerPos.get(1), new Position(1, 3)));
        assertFalse(turnManager.isValidMovement(workerPos.get(0), new Position(1, 0)));

        try {
            turnManager.isValidMovement(null, workerPos.get(1));
        } catch (NullPointerException e) {
            assertEquals("workerPos", e.getMessage());
        }
        try {
            turnManager.isValidMovement(workerPos.get(1), null);
        } catch (NullPointerException e) {
            assertEquals("movePos", e.getMessage());
        }
        try {
            turnManager.moveWorker(null, workerPos.get(1));
        } catch (NullPointerException e) {
            assertEquals("workerPos", e.getMessage());
        }
        try {
            turnManager.moveWorker(workerPos.get(1), null);
        } catch (NullPointerException e) {
            assertEquals("movePos", e.getMessage());
        }
        try {
            turnManager.moveWorker(workerPos.get(2), workerPos.get(1));
        } catch (NotPresentWorkerException e) {
            assertEquals("There isn't a worker of " + PlayerIndex.PLAYER0 +
                    " in : [" + workerPos.get(2).row + "][" + workerPos.get(2).col + "]", e.getMessage());
        }
        try {
            turnManager.moveWorker(workerPos.get(1), new Position(4, 4));
        } catch (NotAdjacentMovementException e) {
            assertEquals(new NotAdjacentMovementException(workerPos.get(1).row, workerPos.get(1).col, 4, 4).getMessage()
                    , e.getMessage());
        }

        turnManager.moveWorker(workerPos.get(1), movementPos.get(0));
        assertSame(PlayerIndex.PLAYER0, gameInstance.getBoard().getOccupiedPlayer(movementPos.get(0)));
        assertTrue(gameInstance.getBoard().isFreeCell(workerPos.get(1)));
        assertFalse(turnManager.hasWonWithMovement());
        gameInstance.build(movementPos.get(0));
        gameInstance.build(movementPos.get(0));
        gameInstance.build(workerPos.get(1));
        gameInstance.build(workerPos.get(1));
        gameInstance.build(workerPos.get(1));
        turnManager.canCurrentPlayerMoveAWorker();
        turnManager.moveWorker(movementPos.get(0), workerPos.get(1));
        assertTrue(turnManager.hasWonWithMovement());


        gameInstance.endTurn();
        turnManager.startTurn();
        assertFalse(turnManager.canCurrentPlayerMoveAWorker());
        gameInstance.endTurn();
        turnManager.startTurn();
        assertTrue(turnManager.canCurrentPlayerMoveAWorker());
        turnManager.endTurn();
    }

    @Test
    public void buildTest() {
        /*
            0A 0  3D 0  0
            0  0A 0  0  0
            0  0  0  0  0
            0  0  0  0  0
            0  0  0  0  0
         */
        List<Position> workerPos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(1, 1),
                new Position(3, 1),
                new Position(4, 1),
                new Position(3, 0),
                new Position(4, 0)
        ));

        List<Position> domePos = new ArrayList<>(List.of(
                new Position(0, 2)
        ));

        workerPos.forEach(pos -> gameInstance.putWorker(pos));

        domePos.forEach(pos -> {
            for (int i = 0; i < 4; i++)
                gameInstance.build(pos);
        });

        turnManager.startTurn();
        try {
            turnManager.buildPositions();
        } catch (NullPointerException e) {
            assertEquals("workerMovedPosition", e.getMessage());
        }
        try {
            turnManager.isValidBuilding(workerPos.get(1));
        } catch (NullPointerException e) {
            assertEquals("workerMovedPosition", e.getMessage());
        }
        try {
            turnManager.buildWorker(workerPos.get(1));
        } catch (NullPointerException e) {
            assertEquals("workerMovedPosition", e.getMessage());
        }
        turnManager.canCurrentPlayerMoveAWorker();
        Position move = new Position(0, 1);
        turnManager.moveWorker(workerPos.get(0), move);

        assertTrue(turnManager.canMovedWorkerBuildInAPosition());
        List<Position> buildPos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(1, 0),
                new Position(1, 2)
        ));

        assertEquals(buildPos, turnManager.buildPositions());
        try {
            turnManager.isValidBuilding(null);
        } catch (NullPointerException e) {
            assertEquals("buildPosition", e.getMessage());
        }

        assertTrue(turnManager.isValidBuilding(buildPos.get(0)));
        assertFalse(turnManager.isValidBuilding(workerPos.get(1)));

        try {
            turnManager.buildWorker(null);
        } catch (NullPointerException e) {
            assertEquals("buildPosition", e.getMessage());
        }
        try {
            turnManager.buildWorker(workerPos.get(4));
        } catch (NotAdjacentBuildingException e) {
            assertEquals(new NotAdjacentBuildingException(move.row, move.col, workerPos.get(4).row, workerPos.get(4).col)
                    .getMessage(), e.getMessage());
        }

        turnManager.buildWorker(buildPos.get(0));

        assertEquals(1, gameInstance.getBoard().getCell(buildPos.get(0)).getLevel());
    }

    @Test
    public void powerPositionsTest(){
        //Put workers on the board
        List<Position> workerPos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 4),
                new Position(0, 2),
                new Position(2, 0),
                new Position(2, 1),
                new Position(4, 4)
        ));

        workerPos.forEach(pos -> gameInstance.putWorker(pos));

        turnManager.startTurn();

        assertEquals(gameInstance.getCurrentPlayerNextState(), GameState.ENDPHASE);

        //Exception check:
        try{
            turnManager.powerPositions(null);
        }
        catch (NullPointerException e){
            assertEquals("workerPos", e.getMessage());
        }

        //block a worker
        turnManager.moveWorker(new Position(0,0), new Position(0,1));

        for(Cell c : gameInstance.getBoard().getAdjacentCells(new Position(0,1))){
            turnManager.buildWorker(c.getPosition());
            turnManager.buildWorker(c.getPosition());
        }

        turnManager.canCurrentPlayerMoveAWorker();

        try{
            turnManager.powerPositions(new Position(0,1));
        }
        catch (NotPresentWorkerException e){
            assertEquals("There isn't a worker of PLAYER0 in : [0][1]", e.getMessage());
        }

        gameInstance.setStartingWorker(new Position(0,4));
        List<Position> powerPos = turnManager.powerPositions(new Position(0,4));

        assertEquals(powerPos.size(),3);
        assertTrue(powerPos.contains(new Position(0,3)));
        assertTrue(powerPos.contains(new Position(1,3)));
        assertTrue(powerPos.contains(new Position(1,4)));

    }

    @Test
    public void validPowerTest(){

        //Put workers on the board
        List<Position> workerPos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 4),
                new Position(0, 2),
                new Position(2, 0),
                new Position(2, 1),
                new Position(4, 4)
        ));

        workerPos.forEach(pos -> gameInstance.putWorker(pos));

        turnManager.startTurn();

        assertEquals(gameInstance.getCurrentPlayerNextState(), GameState.ENDPHASE);

        //Exception check:
        try{
            turnManager.isValidUseOfPower(null, new Position(0,1));
        }
        catch (NullPointerException e){
            assertEquals("workerPos", e.getMessage());
        }
        try{
            turnManager.isValidUseOfPower(new Position(0,0), null);
        }
        catch (NullPointerException e){
            assertEquals("powerPos", e.getMessage());
        }

        //Check returns false if a position without a current player worker is given
        assertFalse(turnManager.isValidUseOfPower(new Position(0,1), new Position(0,1)));

        assertTrue(turnManager.isValidUseOfPower(new Position(0,0), new Position(0,1)));
    }
}