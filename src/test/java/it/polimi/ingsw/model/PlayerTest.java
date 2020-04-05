package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.NotSelectedWorkerException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private static Board board;
    private static Player player;
    private static Position position;
    private static Position newPosition;

    @Before
    public void init(){
        board = new Board();
        player = new Player(board, "Jack", 0);
        position = new Position(0,0);
        newPosition = new Position(1,1);
    }

    @Test
    public void isInitPlayer() {
        assertEquals(0, player.getWorker(0).getPlayerNum());
        assertEquals(0, player.getWorker(1).getPlayerNum());
        assertEquals("Jack", player.getNickName());

        //Workers are initialized with no positionOccupied and no oldPosition
        assertNull(player.getWorker(0).getPositionOccupied());
        assertNull(player.getWorker(1).getPositionOccupied());
        assertNull(player.getWorker(0).getOldPosition());
        assertNull(player.getWorker(1).getOldPosition());
    }

    @Test
    public void putWorkerTest(){

        player.putWorker(position, 0);

        assertEquals(player.getWorkerPositionOccupied(0), position);
        assertNull(player.getWorker(0).getOldPosition());

        //InvalidPositionException check
        try{
            player.putWorker(new Position(-1, 0), 0);
        }
        catch(InvalidPositionException e){
            assertEquals("You cannot have a position in : [" + -1 + "][" + 0 + "]", e.getMessage());
        }

        //InvalidIndexWorkerException check
        try{
            player.putWorker(newPosition, -1);
        }
        catch(InvalidIndexWorkerException e){
            assertEquals("You cannot have a worker with index: " + -1, e.getMessage());
        }


    }
    @Test
    public void moveWorkerTest(){

        player.putWorker(position, 0);

        assertEquals(player.getWorkerPositionOccupied(0), position);
        assertNull(player.getWorker(0).getOldPosition());

        //NotSelectedWorkerException check:
        try{
            player.moveWorker(newPosition);
        }
        catch(NotSelectedWorkerException e){
            assertEquals("No Worker is selected for this operation!", e.getMessage());
        }

        //InvalidPositionException
        try{
            player.moveWorker(new Position(4,7));
        }
        catch (InvalidPositionException e){
            assertEquals("You cannot have a position in : [" + 4 + "][" + 7 + "]", e.getMessage());
        }

        //Correct move check:
        player.setSelectedWorker(0);
        player.moveWorker(newPosition);

        assertEquals(player.getWorkerPositionOccupied(0), newPosition);
        assertEquals(player.getWorker(0).getOldPosition(), position);
    }

    @Test
    public void canMoveTest(){

        //InvalidPositionException: Position given moveToCheck is not a possible position
        try{
            player.canMove(new Position(5,6));
        }
        catch(InvalidPositionException e){
            assertEquals("You cannot have a position in : [" + 5 + "][" + 6 + "]", e.getMessage());
        }

        //NotSelectedWorkerException check:
        try{
            player.canMove(new Position(2,3));
        }
        catch(NotSelectedWorkerException e){
            assertEquals("No Worker is selected for this operation!", e.getMessage());
        }

        Position workerPosition = new Position(1,1);

        player.putWorker(workerPosition, 0);
        player.setSelectedWorker(0);

        //Initialization of the positions related to the adjacent cells
        Position pos00 = new Position(0,0);
        Position pos01 = new Position(0,1);
        Position pos02 = new Position(0,2);
        Position pos10 = new Position(1,0);
        Position pos12 = new Position(1,2);
        Position pos20 = new Position(2,0);
        Position pos21 = new Position(2,1);
        Position pos22 = new Position(2,2);

        //case level 1
        player.getBoard().updateBoardBuild(pos01);

        //case level 2
        player.getBoard().updateBoardBuild(pos02);
        player.getBoard().updateBoardBuild(pos02);

        //case level 3
        player.getBoard().updateBoardBuild(pos10);
        player.getBoard().updateBoardBuild(pos10);
        player.getBoard().updateBoardBuild(pos10);

        //case level 3 + DOME
        player.getBoard().updateBoardBuild(pos12);
        player.getBoard().updateBoardBuild(pos12);
        player.getBoard().updateBoardBuild(pos12);
        player.getBoard().updateBoardBuild(pos12);

        //case level 0 + occupation = PLAYER1
        //adjCell20.setOccupation(CellOccupation.PLAYER1);
        player.putWorker(pos20, 1);

        //case level 1 + occupation = PLAYER2
        player.getBoard().updateBoardBuild(pos21);
        Player p2 = new Player(board, "John", 1);
        p2.putWorker(pos21, 0);

        //case level 2 + occupation = PLAYER3
        player.getBoard().updateBoardBuild(pos22);
        player.getBoard().updateBoardBuild(pos22);
        Player p3 = new Player(board, "Al", 2);
        p3.putWorker(pos22, 0);

        /*Adjacent cells tested reprasention:
        *
        *   0      1      2
        *   3      X      3+D
        *   0+P1   1+P2   2+P3
        *
        * where X is the current worker cell
        * */


        //Check move level 0 -> level 0
        assertTrue(player.canMove(pos00));
        //Check move level 0 -> level 1
        assertTrue(player.canMove(pos01));
        //Check move level 0 -> level 2
        assertFalse(player.canMove(pos02));
        //Check move level 0 -> level 3
        assertFalse(player.canMove(pos10));
        //Check move level 0 -> level 0 + occupation == PLAYER1
        assertFalse(player.canMove(pos20));
        //Check move level 0 -> level 1 + occupation == PLAYER2
        assertFalse(player.canMove(pos21));
        //Check move level 0 -> level 2 + occupation == PLAYER3
        assertFalse(player.canMove(pos22));

        //Upgrade the level of the Cell where worker is to level 1
        player.moveWorker(pos00);
        player.getBoard().updateBoardBuild(workerPosition);
        player.moveWorker(workerPosition);

        //Check move level 1 -> level 0
        assertTrue(player.canMove(pos00));
        //Check move level 1 -> level 2
        assertTrue(player.canMove(pos02));
        //Check move level 1 -> level 3
        assertFalse(player.canMove(pos10));

        //Upgrade the level of the Cell where worker is to level 2
        player.moveWorker(pos00);
        player.getBoard().updateBoardBuild(workerPosition);
        player.moveWorker(workerPosition);

        //Check move level 2 -> level 3 + occupation == DOME
        assertFalse(player.canMove(pos12));
        //Check move level 2 -> level 0
        assertTrue(player.canMove(pos00));
        //Check move level 2 -> level 1
        assertTrue(player.canMove(pos01));
        //Check move level 2 -> level 1
        assertTrue(player.canMove(pos01));

        //Upgrade the level of the Cell where worker is to level 3
        player.moveWorker(pos00);
        player.getBoard().updateBoardBuild(workerPosition);
        player.moveWorker(workerPosition);

        //Check move level 3 -> level 0
        assertTrue(player.canMove(pos00));
        //Check move level 3 -> level 1
        assertTrue(player.canMove(pos01));
        //Check move level 3 -> level 2
        assertTrue(player.canMove(pos02));
        //Check move level 3 -> level 0 + occupation == PLAYER1
        assertFalse(player.canMove(pos20));

        //IllegalArgumentException: Invalid workerLevel value is given, exception check
        /*try{
            player.canMove(cellList, new Position(0,0), 4);
        }
        catch (IllegalArgumentException e){
            assertEquals("Impossible value of worker level!", e.getMessage());
        }*/
    }

    @Test
    public void buildWorkerTest(){
        assertEquals(0, board.getCell(newPosition).getLevel());

        player.buildWorker(newPosition);

        assertEquals(1, board.getCell(newPosition).getLevel());

        player.buildWorker(newPosition);

        assertEquals(2, board.getCell(newPosition).getLevel());

        player.buildWorker(newPosition);

        assertEquals(3, board.getCell(newPosition).getLevel());

        player.buildWorker(newPosition);

        assertEquals(3, board.getCell(newPosition).getLevel());
        assertSame(board.getCell(newPosition).getOccupation(), CellOccupation.DOME);
    }

    @Test
    public void canBuildTest(){

        //InvalidPositionException: Position given buildingPosition is not a possible position
        try{
            player.canBuild(new Position(5,6));
        }
        catch(InvalidPositionException e){
            assertEquals("You cannot have a position in : [" + 5 + "][" + 6 + "]", e.getMessage());
        }

        //NotSelectedWorkerException check:
        try{
            player.canMove(new Position(2,3));
        }
        catch(NotSelectedWorkerException e){
            assertEquals("No Worker is selected for this operation!", e.getMessage());
        }

        //Initialization of the positions related to the adjacent cells
        Position pos00 = new Position(0,0);
        Position pos01 = new Position(0,1);
        Position pos02 = new Position(0,2);
        Position pos10 = new Position(1,0);
        Position pos12 = new Position(1,2);
        Position pos20 = new Position(2,0);
        Position pos21 = new Position(2,1);
        Position pos22 = new Position(2,2);
        Position pos23 = new Position(2,3);

        //case level 1
        player.getBoard().updateBoardBuild(pos01);

        //case level 2
        player.getBoard().updateBoardBuild(pos02);
        player.getBoard().updateBoardBuild(pos02);

        //case level 3
        player.getBoard().updateBoardBuild(pos10);
        player.getBoard().updateBoardBuild(pos10);
        player.getBoard().updateBoardBuild(pos10);

        //case level 3 + DOME
        player.getBoard().updateBoardBuild(pos12);
        player.getBoard().updateBoardBuild(pos12);
        player.getBoard().updateBoardBuild(pos12);
        player.getBoard().updateBoardBuild(pos12);

        //case level 0 + occupation = PLAYER1
        //adjCell20.setOccupation(CellOccupation.PLAYER1);
        player.putWorker(pos20, 1);

        //case level 1 + occupation = PLAYER2
        player.getBoard().updateBoardBuild(pos21);
        Player p2 = new Player(board, "John", 1);
        p2.putWorker(pos21, 0);

        //case level 2 + occupation = PLAYER3
        player.getBoard().updateBoardBuild(pos22);
        player.getBoard().updateBoardBuild(pos22);
        Player p3 = new Player(board, "Al", 2);
        p3.putWorker(pos22, 0);

        /*Adjacent cells tested reprasention:
         *
         *   0      1      2
         *   3      X      3+D
         *   0+P1   1+P2   2+P3
         *
         * where X is the current worker cell
         * */

        //put worker 0 of PLAYER1 on the board
        Position workerPosition = new Position(1,1);
        player.putWorker(workerPosition, 0);

        //Select worker 0
        player.setSelectedWorker(0);

        //Check build on level 0
        assertTrue(player.canBuild(pos00));
        //Check build on level 3
        assertTrue(player.canBuild(pos10));
        //Check build on level 3 + DOME
        assertFalse(player.canBuild(pos12));
        //Check build on level 0 + PLAYER1
        assertFalse(player.canBuild(pos20));
        //Check build on level 1 + PLAYER2
        assertFalse(player.canBuild(pos21));

        //Check build on a not in adjacentCells cell
        assertFalse(player.canBuild(pos23));
    }

    @Test
    public void hasWinTest(){

        //NotSelectedWorkerException check:
        try{
            player.canMove(new Position(2,3));
        }
        catch(NotSelectedWorkerException e){
            assertEquals("No Worker is selected for this operation!", e.getMessage());
        }

        //Select worker 0
        player.setSelectedWorker(0);

        //NullPointerException check (oldPosition and positionOccupied of worker are null):
        try{
            player.hasWin();
        }
        catch(NullPointerException e){
            assertEquals("Worker number 0 has never been moved or other unknown problem", e.getMessage());
        }

        player.putWorker(position, 0);

        //NullPointerException check (only oldPosition is null):
        try{
            player.hasWin();
        }
        catch (NullPointerException e){
            assertEquals("Worker number 0 has never been moved or other unknown problem", e.getMessage());
        }

        //hasWin() after a move level 0 -> level 0
        assertTrue(player.canMove(newPosition));
        player.moveWorker(newPosition);

        assertFalse(player.hasWin());

        //hasWin() after a move level 0 -> level 1
        Position lvl1 = new Position(1,2);
        board.updateBoardBuild(lvl1);

        assertTrue(player.canMove(lvl1));
        player.moveWorker(lvl1);

        assertFalse(player.hasWin());

        //hasWin() after a move level 1 -> level 2
        Position lvl2 = new Position(1,3);
        board.updateBoardBuild(lvl2);
        board.updateBoardBuild(lvl2);

        assertTrue(player.canMove(lvl2));
        player.moveWorker(lvl2);

        assertFalse(player.hasWin());

        //hasWin() after a move level 2 -> level 3 (Win Condition)
        Position lvl3 = new Position(1,4);
        board.updateBoardBuild(lvl3);
        board.updateBoardBuild(lvl3);
        board.updateBoardBuild(lvl3);

        assertTrue(player.canMove(lvl3));
        player.moveWorker(lvl3);

        assertTrue(player.hasWin());
    }

    @Test
    public void blockedWorkersTest(){

        //NullPointerException check:
        try{
            player.blockedWorkers();
        }
        catch(NullPointerException e){
            assertEquals("Worker/s not in any position yet!", e.getMessage());
        }

        //case with two workers free to move --> blockedWorkers().size == 0
        Position posWorker1 = new Position(4,4);
        player.putWorker(position, 0);
        player.putWorker(posWorker1, 1);

        //Select worker 0
        player.setSelectedWorker(0);

        assertEquals(0, player.blockedWorkers().size());

        //case with one worker blocked --> blockedWorkers().size == 1

        //Put a dome in every cell adjacent to workers[0]
        for(Cell c : player.getBoard().getAdjacentCells(player.getWorkerPositionOccupied(0))){
            board.UpdateBoardBuildDome(c.getPosition());
        }

        assertEquals(1,player.blockedWorkers().size());
        assertEquals(0, (int) player.blockedWorkers().get(0));

        //case with two workers blocked --> blockedWorkers().size() == 2

        //Put a dome in every cell adjacent to workers[1]
        for(Cell c : player.getBoard().getAdjacentCells(player.getWorkerPositionOccupied(1))){
            board.UpdateBoardBuildDome(c.getPosition());
        }

        assertEquals(2,player.blockedWorkers().size());
        assertEquals(0, (int) player.blockedWorkers().get(0));
        assertEquals(1, (int) player.blockedWorkers().get(1));
    }

    @Test
    public void isBlockedBuildingTest(){

        //NotSelectedWorkerException check:
        try{
            player.canMove(new Position(2,3));
        }
        catch(NotSelectedWorkerException e){
            assertEquals("No Worker is selected for this operation!", e.getMessage());
        }

        //Select worker 0
        player.setSelectedWorker(0);

        //NullPointerException check:
        try{
            player.isBlockedBuilding();
        }
        catch(NullPointerException e){
            assertEquals("Worker/s not in any position yet!", e.getMessage());
        }

        //case worker free to build in all positions
        player.putWorker(position, 0);

        assertFalse(player.isBlockedBuilding());

        //case builds blocked in some positions
        player.getBoard().UpdateBoardBuildDome(new Position(0,1));

        assertFalse(player.isBlockedBuilding());

        //case builds blocked in all positions adjacent to the worker
        player.getBoard().UpdateBoardBuildDome(new Position(1,0));
        player.getBoard().UpdateBoardBuildDome(new Position(1,1));

        assertTrue(player.isBlockedBuilding());
    }
}
