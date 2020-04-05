package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisDecoratorTest {

    private static Board board;
    private static ArtemisDecorator artemis;
    private static int indexWorker;


    @Before
    public void init(){
        board = new Board();
        artemis = new ArtemisDecorator(new Player(board, "Jack", 0));
        Position startPos = new Position(1, 1);
        indexWorker = 0;
        artemis.setSelectedWorker(indexWorker);
        artemis.putWorker(startPos, 0);
    }

    @Test
    public void isCanUsePowerCorrected(){

        Position finalPos = new Position(1,3);

        artemis.moveWorker(new Position(1,2));
        assertEquals(board.getCell(new Position(1,2)).getOccupation(), CellOccupation.PLAYER1);
        assertFalse(artemis.canUsePower(new Position(1, 1)));
        assertTrue(artemis.canUsePower(finalPos));
        artemis.usePower(finalPos);

        assertEquals(artemis.getWorkerPositionOccupied(indexWorker), finalPos);
    }
}
