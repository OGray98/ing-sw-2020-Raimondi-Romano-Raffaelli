package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidIncrementLevelException;
import it.polimi.ingsw.exceptions.NotSamePositionException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class HephaestusDecoratorTest {


    private static Board board;
    PlayerInterface player;
    private static   Position workerPosition;
    private static   Position buildPosition;
    private static   Position differentBuildPosition;

    @Before
    public void setUp(){

        board = new Board();
        player = new HephaestusDecorator(new Player(board,"rock",1));
        workerPosition = new Position(2,2);
        buildPosition = new Position(2,3);
        differentBuildPosition = new Position(2,1);
    }

    @Test
    public void CanUsePowerTest() {

        player.putWorker(workerPosition,1);
        player.setSelectedWorker(1);

        assertTrue(player.canBuild(buildPosition));
        player.buildWorker(buildPosition);
        assertTrue(player.canUsePower(buildPosition));

        player.usePower(buildPosition);
        assertEquals(player.getBoard().getCell(buildPosition).getLevel(),2);

        player.buildWorker(buildPosition);
        assertEquals(player.getBoard().getCell(buildPosition).getLevel(),3);
        player.buildWorker(buildPosition);
        assertEquals(player.getBoard().getCell(buildPosition).getOccupation(), CellOccupation.DOME);
        try{
            player.canUsePower(buildPosition);
        }catch(InvalidIncrementLevelException e){
            assertEquals("You cannot build in cell : [" + buildPosition.row +"][" + buildPosition.col + "]",e.getMessage());
        }

        try{
            player.canUsePower(differentBuildPosition);
        }catch(NotSamePositionException e){
            assertEquals("You can't build in: [" + differentBuildPosition.row + "][" + differentBuildPosition.col + "] is a different position",e.getMessage());
        }


    }

}