package it.polimi.ingsw.message;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        ConnectionPlayerIndexMessage msg = new ConnectionPlayerIndexMessage(PlayerIndex.PLAYER0);
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

    @Test
    public void okMessageTest() {
        OkMessage msg = new OkMessage(PlayerIndex.PLAYER0, TypeMessage.WINNER, "D");
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.INF, stub.n);
        assertEquals("D", msg.getErrorMessage());
        msg = new OkMessage(PlayerIndex.PLAYER0, TypeMessage.LOSER, "D");
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.UP_L, stub.n);
    }

    @Test
    public void playerSelectGodMessageMessageTest() {
        PlayerSelectGodMessage msg = new PlayerSelectGodMessage(PlayerIndex.PLAYER0, "Apollo");
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.UP_C, stub.n);
    }

    @Test
    public void positionMessageMessageTest() {
        PositionMessage msg = new PositionMessage(PlayerIndex.PLAYER0, pos, false);
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.UP_A, stub.n);
        assertEquals(pos, msg.getPosition());
        assertFalse(msg.isUsingPower());
    }

    @Test
    public void putWorkerMessageTest() {
        Position pos2 = new Position(2, 2);
        PutWorkerMessage msg = new PutWorkerMessage(PlayerIndex.PLAYER0, pos, pos2);
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.PUT, stub.n);
    }

    @Test
    public void removePlayerMessageTest() {
        List<Position> positions = new ArrayList<>(List.of(pos));
        RemovePlayerMessage msg = new RemovePlayerMessage(PlayerIndex.PLAYER0, positions);
        msg.execute(stub);
        assertEquals(StubControllableByViewMessage.REM_P, stub.n);
        assertEquals(positions, msg.getRemovePositions());

        try {
            new RemovePlayerMessage(PlayerIndex.PLAYER0, null);
        } catch (NullPointerException e) {
            assertEquals("removePositions", e.getMessage());
        }
    }


    @After
    public void delete() {
        stub = null;
        pos = null;
    }
}
