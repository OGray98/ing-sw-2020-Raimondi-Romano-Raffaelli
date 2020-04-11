package org.example;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BoardTest {

    private static Position domePosition;
    private static Position workerPosition;
    private static Position freePosition;
    private static Position zeroPosition;
    private static Position opponentAdjacentWorkerPosition;
    private static Board board;
    private static Map<PositionContainer, PlayerIndex> playerPosition;




    @Before
    public void init(){
        board = new Board();
        domePosition = new Position(1,1);
        freePosition = new Position(3,2);
        opponentAdjacentWorkerPosition = new Position(3,4);
        workerPosition = new Position(3,3);
        zeroPosition = new Position(0,0);
    }

    @Test
    public void isBoardInit(){
        //control if init board is set correctly
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++) {
                assertEquals(0, board.getCell(new Position(i,j)).getLevel());
                assertEquals(new Position(i,j),board.getCell(new Position(i,j)).getPosition());
                assertEquals(new Cell(i,j),board.getCell(new Position(i,j)));
                assertFalse(board.getCell(new Position(i,j)).hasDome());
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

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        assertFalse(board.isFreeCell(workerPosition));

    }


    @Test
    public void isGetAdjacentPlayerCorrect() {

        try{
            board.getAdjacentPlayers(null);
        }catch(NullPointerException e) {
            assertEquals("centralPosition", e.getMessage());
        }

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        board.putWorker(freePosition,PlayerIndex.PLAYER1);
        board.putWorker(opponentAdjacentWorkerPosition,PlayerIndex.PLAYER2);
        Map<Position, PlayerIndex> adjacentPlayer;
        adjacentPlayer = board.getAdjacentPlayers(workerPosition);
        assertEquals(adjacentPlayer.get(freePosition),PlayerIndex.PLAYER1);
        assertEquals(adjacentPlayer.get(opponentAdjacentWorkerPosition),PlayerIndex.PLAYER2);

        board.putWorker(zeroPosition,PlayerIndex.PLAYER0);
        Map<Position, PlayerIndex> adjacentPlayerSecondWorker;
        adjacentPlayerSecondWorker = board.getAdjacentPlayers(zeroPosition);
        assertEquals(0,adjacentPlayerSecondWorker.size());
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

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        board.changeWorkerPosition(workerPosition,freePosition);
        assertEquals(board.getOccupiedPlayer(freePosition),PlayerIndex.PLAYER0);

    }

    @Test
    public void getAdjacentCellTest(){
        try{
            board.getAdjacentCells(null);
        }catch(NullPointerException e){
            assertEquals("centralPosition",e.getMessage());
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertTrue(isStandardCaseCorrected(board.getAdjacentCells(new Position(i, j)), i, j));
            }
        }
    }

    @Test
    public void putWorkerTest(){
        try{
            board.putWorker(null,PlayerIndex.PLAYER0);
        }catch(NullPointerException e){
            assertEquals("putPosition",e.getMessage());
        }

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        assertEquals(board.getOccupiedPlayer(workerPosition),PlayerIndex.PLAYER0);
        board.putWorker(freePosition,PlayerIndex.PLAYER0);
        try{
            board.putWorker(new Position(1,3),PlayerIndex.PLAYER0);
        }catch(InvalidPutWorkerException e){
            assertEquals(PlayerIndex.PLAYER0 + " cannot put a worker in : [1][3], because he already have 2 worker in your board",e.getMessage());
        }
    }

    @Test
    public void getOccupiedPlayerTest(){
        try{
            board.getOccupiedPlayer(null);
        }catch (NullPointerException e){
            assertEquals("position",e.getMessage());
        }

        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        assertEquals(PlayerIndex.PLAYER0,board.getOccupiedPlayer(workerPosition));

        try{
            board.getOccupiedPlayer(freePosition);
        }catch (NotPresentWorkerException e){
            assertEquals("There isn't a player in : [" + freePosition.row + "][" + freePosition.col + "]",e.getMessage());
        }
    }

    @Test
    public void workerPositionsTest(){
        //MissingWorkersException:
        try {
            board.workerPositions(PlayerIndex.PLAYER0);
        } catch (MissingWorkerException e) {
            assertEquals("A player has" + 0 + "workers on the map, must be 2", e.getMessage());
        }
        board.putWorker(new Position(1, 1), PlayerIndex.PLAYER1);
        board.putWorker(new Position(3, 3), PlayerIndex.PLAYER1);

        Position pos11 = new Position(1, 1);
        Position pos33 = new Position(3, 3);

        assertEquals(board.workerPositions(PlayerIndex.PLAYER1).size(), 2);
        assertTrue(board.workerPositions(PlayerIndex.PLAYER1).contains(pos11));
        assertTrue(board.workerPositions(PlayerIndex.PLAYER1).contains(pos33));

    }

    @Test
    public void isUpdateAfterPowerCorrected() {
        Position old01 = new Position(1, 2);
        Position new01 = new Position(1, 3);
        Position old10 = new Position(2, 4);
        Position new10 = new Position(0, 4);

        Position buildPos = new Position(3, 3);

        board.putWorker(old01, PlayerIndex.PLAYER0);
        board.putWorker(old10, PlayerIndex.PLAYER1);

        BoardChange boardChange = new BoardChange(old01, new01, PlayerIndex.PLAYER0);
        boardChange.addPlayerChanges(old10, new10, PlayerIndex.PLAYER1);
        board.updateAfterPower(boardChange);

        assertTrue(board.isFreeCell(old01));
        assertTrue(board.isFreeCell(old10));
        assertEquals(PlayerIndex.PLAYER0, board.getOccupiedPlayer(new01));
        assertEquals(PlayerIndex.PLAYER1, board.getOccupiedPlayer(new10));

        assertTrue(boardChange.isCantGoUpNull());
        assertFalse(boardChange.isPlayerChangesNull());
        assertTrue(boardChange.isPositionBuildNull());

        boardChange = new BoardChange(true);
        board.updateAfterPower(boardChange);

        assertTrue(board.isCantGoUp());

        assertFalse(boardChange.isCantGoUpNull());
        assertTrue(boardChange.isPlayerChangesNull());
        assertTrue(boardChange.isPositionBuildNull());

        boardChange = new BoardChange(buildPos, BuildType.LEVEL);
        board.updateAfterPower(boardChange);

        assertEquals(1, board.getCell(buildPos).getLevel());
        assertFalse(board.getCell(buildPos).hasDome());

        assertTrue(boardChange.isCantGoUpNull());
        assertTrue(boardChange.isPlayerChangesNull());
        assertFalse(boardChange.isPositionBuildNull());


        boardChange = new BoardChange(buildPos, BuildType.DOME);
        board.updateAfterPower(boardChange);

        assertEquals(1, board.getCell(buildPos).getLevel());
        assertTrue(board.getCell(buildPos).hasDome());

        assertTrue(boardChange.isCantGoUpNull());
        assertTrue(boardChange.isPlayerChangesNull());
        assertFalse(boardChange.isPositionBuildNull());

        boardChange = new BoardChange(buildPos, BuildType.DOME);
        try {
            board.updateAfterPower(boardChange);
        } catch (InvalidBuildDomeException e) {
            assertEquals("There already is a dome in position [" + buildPos.row + "][" + buildPos.col + "]",
                    e.getMessage());
        }

        try {
            board.updateAfterPower(null);
        } catch (NullPointerException e) {
            assertEquals("boardChange", e.getMessage());
        }

        boardChange = new BoardChange(new Position(4, 4), new01, PlayerIndex.PLAYER2);
        try {
            board.updateAfterPower(boardChange);
        } catch (NotPresentWorkerException e) {
            assertEquals("There isn't a worker of " + PlayerIndex.PLAYER2 + " in : [" + 4 + "][" + 4 + "]",
                    e.getMessage());
        }
    }


    private boolean isCornerUpLeftCorrected(List<Cell> cells) {
        return cells.size() == 3 && board.getCell(new Position(0, 1)).equals(cells.get(0)) && board.getCell(new Position(1, 0)).equals(cells.get(1)) && board.getCell(new Position(1, 1)).equals(cells.get(2));
    }

    private boolean isCornerDownRightCorrected(List<Cell> cells) {
        return cells.size() == 3 && board.getCell(new Position(3, 3)).equals(cells.get(0)) && board.getCell(new Position(3, 4)).equals(cells.get(1)) && board.getCell(new Position(4, 3)).equals(cells.get(2));
    }

    private boolean isCornerUpRightCorrected(List<Cell> cells) {
        return cells.size()== 3 && board.getCell(new Position(0,3)).equals(cells.get(0)) && board.getCell(new Position(1,3)).equals(cells.get(1)) && board.getCell(new Position(1,4)).equals(cells.get(2));
    }

    private boolean isCornerDownLeftCorrected(List<Cell> cells) {
        return cells.size()== 3 && board.getCell(new Position(3,0)).equals(cells.get(0)) && board.getCell(new Position(3,1)).equals(cells.get(1)) && board.getCell(new Position(4,1)).equals(cells.get(2));
    }

    private boolean isBoundaryUpCorrected(List<Cell> cells, int c) {
        if (c == 0)
            return isCornerUpLeftCorrected(cells);
        if (c == 4)
            return isCornerUpRightCorrected(cells);
        return cells.size() == 5 && board.getCell(new Position(0,c-1)).equals(cells.get(0)) && board.getCell(new Position(0,c+1)).equals(cells.get(1)) && board.getCell(new Position(1,c-1)).equals(cells.get(2))
                && board.getCell(new Position(1,c)).equals(cells.get(3)) && board.getCell(new Position(1,c+1)).equals(cells.get(4));
    }

    private boolean isBoundaryDownCorrected(List<Cell> cells, int c) {
        if (c == 0)
            return isCornerDownLeftCorrected(cells);
        if (c == 4)
            return isCornerDownRightCorrected(cells);
        return cells.size() == 5 && board.getCell(new Position(3,c-1)).equals(cells.get(0)) && board.getCell(new Position(3,c)).equals(cells.get(1)) && board.getCell(new Position(3,c+1)).equals(cells.get(2))
                && board.getCell(new Position(4,c-1)).equals(cells.get(3)) && board.getCell(new Position(4,c+1)).equals(cells.get(4));
    }

    private boolean isBoundaryLeftCorrected(List<Cell> cells, int r) {
        if (r == 0)
            return isCornerUpLeftCorrected(cells);
        if (r == 4)
            return isCornerDownLeftCorrected(cells);
        return cells.size() == 5 && board.getCell(new Position(r-1,0)).equals(cells.get(0)) && board.getCell(new Position(r-1,1)).equals(cells.get(1)) && board.getCell(new Position(r,1)).equals(cells.get(2))
                && board.getCell(new Position(r+1,0)).equals(cells.get(3)) && board.getCell(new Position(r+1,1)).equals(cells.get(4));
    }

    private boolean isBoundaryRightCorrected(List<Cell> cells, int r) {
        if (r == 0)
            return isCornerUpRightCorrected(cells);
        if (r == 4)
            return isCornerDownRightCorrected(cells);
        return cells.size() == 5 && board.getCell(new Position(r-1,3)).equals(cells.get(0)) && board.getCell(new Position(r-1,4)).equals(cells.get(1)) && board.getCell(new Position(r,3)).equals(cells.get(2))
                && board.getCell(new Position(r+1,3)).equals(cells.get(3)) && board.getCell(new Position(r+1,4)).equals(cells.get(4));
    }

    private boolean isStandardCaseCorrected(List<Cell> cells, int r, int c) {
        if(r == 0)
            return isBoundaryUpCorrected(cells, c);
        else if(r == 4)
            return isBoundaryDownCorrected(cells, c);
        if(c == 0)
            return isBoundaryLeftCorrected(cells, r);
        else if(c == 4)
            return isBoundaryRightCorrected(cells, r);

        return cells.size() == 8 && board.getCell(new Position(r-1,c-1)).equals(cells.get(0)) && board.getCell(new Position(r-1,c)).equals(cells.get(1)) && board.getCell(new Position(r-1,c+1)).equals(cells.get(2))
                && board.getCell(new Position(r,c-1)).equals(cells.get(3)) && board.getCell(new Position(r,c+1)).equals(cells.get(4))
                && board.getCell(new Position(r+1,c-1)).equals(cells.get(5)) && board.getCell(new Position(r+1,c)).equals(cells.get(6)) && board.getCell(new Position(r+1,c+1)).equals(cells.get(7));
    }
}