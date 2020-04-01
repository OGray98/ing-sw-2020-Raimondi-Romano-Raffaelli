package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private static Board board;
    private static Player player;
    private static Position position;
    private static Position newPosition;
    private static List<Cell> cellList;

    @BeforeClass
    public static void init(){
        board = new Board();
        player = new Player(board, "Jack", 1);
        position = new Position(0,0);
        newPosition = new Position(1,1);
        cellList = new ArrayList<>();
    }

    @Test
    public void isInitPlayer() {
        assertTrue(player.getWorker(0).getPlayerNum() == 1);
        assertTrue(player.getWorker(1).getPlayerNum() == 1);
        assertEquals("Jack", player.getNickName());

        //Workers are initialized with no positionOccupied and no oldPosition
        assertNull(player.getWorker(0).getPositionOccupied());
        assertNull(player.getWorker(1).getPositionOccupied());
        assertNull(player.getWorker(0).getOldPosition());
        assertNull(player.getWorker(1).getOldPosition());
    }

    @Test
    public void moveWorkerTest(){

        player.moveWorker(position, 0);

        assertEquals(player.getWorkerPositionOccupied(0), position);
        assertNull(player.getWorker(0).getOldPosition());

        player.moveWorker(newPosition, 0);

        assertEquals(player.getWorkerPositionOccupied(0), newPosition);
        assertEquals(player.getWorker(0).getOldPosition(), position);

        //Exceptions check:

        //Invalid index of worker
        try{
            player.moveWorker(position, -1);
        }
        catch(InvalidIndexWorkerException e){
            assertEquals("You cannot have a worker with index: " + -1, e.getMessage());
        }
    }

    @Test
    public void canMoveTest(){

        //NullPointerException: Null adjacentCells is given, exception check
        try{
            player.canMove(cellList, new Position(0,0), 2);
        }
        catch (NullPointerException e){
            assertEquals("adjacentCells is null!", e.getMessage());
        }

        //InvalidPositionException: Position given moveToCheck is not a possible position
        try{
            player.canMove(cellList, new Position(5,6), 2);
        }
        catch(InvalidPositionException e){
            assertEquals("You cannot have a position in : [" + 5 + "][" + 6 + "]", e.getMessage());
        }

        //Initialization of the Cells adjacent, assuming they are actually adjacent (this is tested in BoardTest class)
        //Assuming adjacentCells of board[1][1] cell
        Cell adjCell00 = new Cell(0,0);
        Cell adjCell01 = new Cell(0,1);
        Cell adjCell02 = new Cell(0,2);
        Cell adjCell10 = new Cell(1,0);
        Cell adjCell12 = new Cell(1,2);
        Cell adjCell20 = new Cell(2,0);
        Cell adjCell21 = new Cell(2,1);
        Cell adjCell22 = new Cell(2,2);

        //case level 1
        adjCell01.incrementLevel();

        //case level 2
        adjCell02.incrementLevel();
        adjCell02.incrementLevel();

        //case level 3
        adjCell10.incrementLevel();
        adjCell10.incrementLevel();
        adjCell10.incrementLevel();

        //case level 3 + DOME
        adjCell12.incrementLevel();
        adjCell12.incrementLevel();
        adjCell12.incrementLevel();
        adjCell12.incrementLevel();

        //case level 0 + occupation = PLAYER1
        adjCell20.setOccupation(CellOccupation.PLAYER1);

        //case level 1 + occupation = PLAYER2
        adjCell21.incrementLevel();
        adjCell21.setOccupation(CellOccupation.PLAYER2);

        //case level 2 + occupation = PLAYER3
        adjCell22.incrementLevel();
        adjCell22.incrementLevel();
        adjCell22.setOccupation(CellOccupation.PLAYER3);

        cellList.add(adjCell00);
        cellList.add(adjCell01);
        cellList.add(adjCell02);
        cellList.add(adjCell10);
        cellList.add(adjCell12);
        cellList.add(adjCell20);
        cellList.add(adjCell21);
        cellList.add(adjCell22);

        //IllegalArgumentException: Invalid workerLevel value is given, exception check
        try{
            player.canMove(cellList, new Position(0,0), 4);
        }
        catch (IllegalArgumentException e){
            assertEquals("Impossible value of worker level!", e.getMessage());
        }

        /*Adjacent cells tested reprasention:
        *
        *   0      1      2
        *   3      X      3+D
        *   0+P1   1+P2   2+P3
        *
        * where X is the current worker cell
        * */

        //Initialization of the positions related to the adjacent cells
        Position pos00 = new Position(0,0);
        Position pos01 = new Position(0,1);
        Position pos02 = new Position(0,2);
        Position pos10 = new Position(1,0);
        Position pos12 = new Position(1,2);
        Position pos20 = new Position(2,0);
        Position pos21 = new Position(2,1);
        Position pos22 = new Position(2,2);
        //not in adjacent cell position:
        Position pos23 = new Position(2,3);


        //To test the following we are assuming that workerLevel is always the correct current level of the worker
        //(this condition will be checked in the related method in the Controller)

        //Check move level 0 -> level 0
        assertTrue(player.canMove(cellList, pos00, 0));
        //Check move level 0 -> level 1
        assertTrue(player.canMove(cellList, pos01, 0));
        //Check move level 0 -> level 2
        assertFalse(player.canMove(cellList, pos02, 0));
        //Check move level 0 -> level 3
        assertFalse(player.canMove(cellList, pos10, 0));
        //Check move level 0 -> level 0 + occupation == PLAYER1
        assertFalse(player.canMove(cellList, pos20,0));
        //Check move level 0 -> level 1 + occupation == PLAYER2
        assertFalse(player.canMove(cellList, pos21, 0));
        //Check move level 0 -> level 2 + occupation == PLAYER3
        assertFalse(player.canMove(cellList, pos22, 0));
        //Check move level 2 -> level 3 + occupation == DOME
        assertFalse(player.canMove(cellList, pos12,2));
        //Check move level 1 -> level 0
        assertTrue(player.canMove(cellList, pos00, 1));
        //Check move level 1 -> level 2
        assertTrue(player.canMove(cellList, pos02,1));
        //Check move level 1 -> level 3
        assertFalse(player.canMove(cellList, pos10,1));
        //Check move level 2 -> level 0
        assertTrue(player.canMove(cellList, pos00, 2));
        //Check move level 2 -> level 1
        assertTrue(player.canMove(cellList, pos01, 2));
        //Check move level 3 -> level 0
        assertTrue(player.canMove(cellList, pos00,3));
        //Check move level 3 -> level 1
        assertTrue(player.canMove(cellList, pos01, 3));
        //Check move level 3 -> level 2
        assertTrue(player.canMove(cellList, pos02,3));
        //Check move level 3 -> level 0 + occupation == PLAYER1
        assertFalse(player.canMove(cellList, pos20, 3));
        //Check move level 2 -> level 1
        assertTrue(player.canMove(cellList, pos01,2));
        //Check move level 0 -> not in adjacentCells list cell
        assertFalse(player.canMove(cellList, pos23, 0));

    }

    @Test
    public void buildWorkerTest(){
        assertTrue(board.getCell(newPosition).getLevel() == 0);

        player.buildWorker(newPosition);

        assertTrue(board.getCell(newPosition).getLevel() == 1);

        player.buildWorker(newPosition);

        assertTrue(board.getCell(newPosition).getLevel() == 2);

        player.buildWorker(newPosition);

        assertTrue(board.getCell(newPosition).getLevel() == 3);

        player.buildWorker(newPosition);

        assertTrue(board.getCell(newPosition).getLevel() == 3);
        assertTrue(board.getCell(newPosition).getOccupation() == CellOccupation.DOME);
    }

    @Test
    public void canBuildTest(){

        //NullPointerException: Null adjacentCells is given, exception check
        try{
            player.canBuild(cellList, new Position(0,0));
        }
        catch (NullPointerException e){
            assertEquals("adjacentCells is null!", e.getMessage());
        }
        //InvalidPositionException: Position given buildingPosition is not a possible position
        try{
            player.canBuild(cellList, new Position(5,6));
        }
        catch(InvalidPositionException e){
            assertEquals("You cannot have a position in : [" + 5 + "][" + 6 + "]", e.getMessage());
        }
        //Initialization of the Cells adjacent, assuming they are actually adjacent (this is tested in BoardTest class)
        //Assuming adjacentCells of board[1][1] cell
        Cell adjCell00 = new Cell(0,0);
        Cell adjCell01 = new Cell(0,1);
        Cell adjCell02 = new Cell(0,2);
        Cell adjCell10 = new Cell(1,0);
        Cell adjCell12 = new Cell(1,2);
        Cell adjCell20 = new Cell(2,0);
        Cell adjCell21 = new Cell(2,1);
        Cell adjCell22 = new Cell(2,2);

        //case level 1
        adjCell01.incrementLevel();

        //case level 2
        adjCell02.incrementLevel();
        adjCell02.incrementLevel();

        //case level 3
        adjCell10.incrementLevel();
        adjCell10.incrementLevel();
        adjCell10.incrementLevel();

        //case level 3 + DOME
        adjCell12.incrementLevel();
        adjCell12.incrementLevel();
        adjCell12.incrementLevel();
        adjCell12.incrementLevel();

        //case level 0 + occupation = PLAYER1
        adjCell20.setOccupation(CellOccupation.PLAYER1);

        //case level 1 + occupation = PLAYER2
        adjCell21.incrementLevel();
        adjCell21.setOccupation(CellOccupation.PLAYER2);

        //case level 2 + occupation = PLAYER3
        adjCell22.incrementLevel();
        adjCell22.incrementLevel();
        adjCell22.setOccupation(CellOccupation.PLAYER3);

        cellList.add(adjCell00);
        cellList.add(adjCell01);
        cellList.add(adjCell02);
        cellList.add(adjCell10);
        cellList.add(adjCell12);
        cellList.add(adjCell20);
        cellList.add(adjCell21);
        cellList.add(adjCell22);

        /*Adjacent cells tested reprasention:
         *
         *   0      1      2
         *   3      X      3+D
         *   0+P1   1+P2   2+P3
         *
         * where X is the current worker cell
         * */

        //Initialization of the positions related to the adjacent cells
        Position pos00 = new Position(0,0);
        Position pos01 = new Position(0,1);
        Position pos02 = new Position(0,2);
        Position pos10 = new Position(1,0);
        Position pos12 = new Position(1,2);
        Position pos20 = new Position(2,0);
        Position pos21 = new Position(2,1);
        Position pos22 = new Position(2,2);
        //not in adjacent cell position:
        Position pos23 = new Position(2,3);

        //Check build on level 0
        assertTrue(player.canBuild(cellList, pos00));
        //Check build on level 3
        assertTrue(player.canBuild(cellList, pos10));
        //Check build on level 3 + DOME
        assertFalse(player.canBuild(cellList, pos12));
        //Check build on level 0 + PLAYER1
        assertFalse(player.canBuild(cellList, pos20));
        //Check build on level 1 + PLAYER2
        assertFalse(player.canBuild(cellList, pos21));
        //Check build on a not in adjacentCells cell
        assertFalse(player.canBuild(cellList, pos23));
    }

}
