package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class BoardTest {

    private static Position domePosition;
    private static Position workerPosition;
    private static Position freePosition;
    private static Board board;
    private static Map<PositionContainer, PlayerIndex> playerPosition;


    @Before
    public void init() {
        board = new Board();
        domePosition = new Position(1, 1);
        freePosition = new Position(3, 2);
    }

    @Test
    public void isConstructBlockCorrected() {
        assertEquals(0, board.getCell(freePosition).getLevel());

        board.constructBlock(freePosition);
        assertEquals(1, board.getCell(freePosition).getLevel());

        board.constructBlock(freePosition);
        assertEquals(2, board.getCell(freePosition).getLevel());

        board.constructBlock(freePosition);
        assertEquals(3, board.getCell(freePosition).getLevel());
        assertFalse(board.getCell(freePosition).hasDome());

        board.constructBlock(freePosition);
        assertEquals(3, board.getCell(freePosition).getLevel());
        assertTrue(board.getCell(freePosition).hasDome());
    }

    @Test
    public void isBoardInit() {
        //control if init board is set correctly
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, board.getCell(new Position(i, j)).getLevel());
                assertEquals(new Position(i, j), board.getCell(new Position(i, j)).getPosition());
                assertEquals(new Cell(i, j), board.getCell(new Position(i, j)));
                assertFalse(board.getCell(new Position(i, j)).hasDome());
            }
        }
    }

    @Test
    public void freeCellTest() {
        try{
            board.isFreeCell(null);
        }catch(NullPointerException e){
            assertEquals("cellPosition",e.getMessage());
        }
        try {
            board.isFreeCell(new Position(5, 5));
        }catch (InvalidPositionException e){
            assertEquals("You cannot have a position in : [5][5]",e.getMessage());
        }
        try {
            board.isFreeCell(new Position(-1, -1));
        }catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [-1][-1]", e.getMessage());
        }

        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++) {
                assertTrue(board.isFreeCell(new Position(i, j)));
            }
        }

        board.constructBlock(domePosition);
        board.constructBlock(domePosition);
        board.constructBlock(domePosition);
        board.constructBlock(domePosition); // set dome

        assertFalse(board.isFreeCell(domePosition));

    }


    @Test
    public void isGetAdjacentListCorrect() {

        try{
            board.getAdjacentPlayers(null);
        }catch(NullPointerException e){
            assertEquals("centralPosition",e.getMessage());
        }

    }

    @Test
    public void getCellTest() {
        try{
            board.getCell(null);
        }catch(NullPointerException e){
            assertEquals("Position",e.getMessage());
        }
        for (int i = 0;i < 5;i++){
            for (int j = 0;j < 5;j++){
                assertEquals(new Cell(i,j),board.getCell(new Position(i,j)));
            }
        }
    }

    @Test
    public void constructBlockTest() {
        try{
            board.constructBlock(null);
        }catch(NullPointerException e){
            assertEquals("buildPosition",e.getMessage());
        }
        for (int i = 0;i < 5;i++){
            for (int j = 0;j < 5;j++) {
                board.constructBlock(new Position(i,j));
                assertEquals(1,board.getCell(new Position(i,j)).getLevel());
            }
        }

    }

    @Test
    public void changeWorkerTest() {

        try{
            board.changeWorkerPosition(new Position(2,1),null);
        }catch(NullPointerException e){
            assertEquals("newPosition",e.getMessage());
        }

        try{
            board.changeWorkerPosition(null,new Position(3,1));
        }catch(NullPointerException e){
            assertEquals("oldPosition",e.getMessage());
        }

        try{
            board.changeWorkerPosition(freePosition,new Position(4,4));
        }catch(NotPresentWorkerException e){
            assertEquals("There isn't a player in : [" + freePosition.row + "][" + freePosition.col + "]",e.getMessage());
        }


    }

    public void getAdjacentCellTest(){
        try{
            board.getAdjacentCells(null);
        }catch(NullPointerException e){
            assertEquals("centralPosition",e.getMessage());
        }
    }


}