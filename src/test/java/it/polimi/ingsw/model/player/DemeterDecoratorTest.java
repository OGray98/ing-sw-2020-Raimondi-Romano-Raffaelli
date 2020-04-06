package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterDecoratorTest {

    private static Board board;
    private static PlayerInterface player;
    private static Position workerPosition;
    private static Position firstBuildingPosition;
    private static Position sameAsFirst;
    private static Position secondBuildingPosition;
    private static Position nullPos;

    @Before
    public void init(){
        board = new Board();
        player = new DemeterDecorator(new Player(board, "Jack", 1));
        workerPosition = new Position(2,2);
        firstBuildingPosition = new Position(2,3);
        sameAsFirst = new Position(2,3);
        secondBuildingPosition = new Position(3,2);
    }

    @Test
    public void canUsePowerTest(){
        player.putWorker(workerPosition, 1);
        player.setSelectedWorker(1);

        assertTrue(player.canBuild(firstBuildingPosition));

        player.buildWorker(firstBuildingPosition);

        assertFalse(player.canUsePower(sameAsFirst));
        assertFalse(player.canUsePower(new Position(2,4)));
        assertTrue(player.canUsePower(secondBuildingPosition));

        player.usePower(secondBuildingPosition);

        assertEquals(player.getBoard().getCell(secondBuildingPosition).getLevel(), 1);

        //NullPointerException check:
        try{
            player.canUsePower(nullPos);
        }
        catch (NullPointerException e){
            assertEquals("firstBuildingPosition is null!", e.getMessage());
        }
    }

    /* usePower() is a simple call to buildWorker() so it does not need to be tested */
}
