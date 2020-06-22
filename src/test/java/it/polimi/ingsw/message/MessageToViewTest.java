package it.polimi.ingsw.message;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageToViewTest {

    private StubControllableByViewMessage stub = new StubControllableByViewMessage();
    private Position pos;

    @Before
    public void init() {
        pos = new Position(1, 1);
    }

    @Test
    public void buildViewMessageTest() {
        BuildViewMessage msg = new BuildViewMessage(PlayerIndex.PLAYER0, pos, 1);
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.BUILD, stub.n);
        assertEquals(1, msg.getLevel());
        assertEquals(pos, msg.getBuildPosition());
    }

    @Test
    public void connectionPlayerIndexMessageTest() {
        ConnectionPlayerIndex msg = new ConnectionPlayerIndex(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.UP_I, stub.n);
    }

    @Test
    public void errorMessageTest() {
        ErrorMessage msg = new ErrorMessage(
                PlayerIndex.PLAYER0, TypeMessage.NICKNAME, "D"
        );
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.EN, stub.n);
        assertEquals("D", msg.getErrorMessage());
        assertEquals(TypeMessage.NICKNAME, msg.getSpecificErrorType());
    }

    @Test
    public void informationMessageTest() {
        InformationMessage msg = new InformationMessage(
                PlayerIndex.PLAYER0, TypeMessage.BUILD, TypeMessage.NICKNAME, "W"
        );
        msg.execute(stub);
    }

    @Test
    public void moveMessageTest() {
        Position pos2 = new Position(2, 2);
        MoveMessage msg = new MoveMessage(PlayerIndex.PLAYER0, pos, pos2);
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.MOVE, stub.n);
        assertEquals(pos2, msg.getMovePosition());
        assertEquals(pos, msg.getWorkerPosition());
    }


    @After
    public void delete() {
        stub = null;
        pos = null;
    }
}
