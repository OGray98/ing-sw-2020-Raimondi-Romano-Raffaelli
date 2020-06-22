package it.polimi.ingsw.message;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageToClientTest {

    private StubControllableByServerMessage stub = new StubControllableByServerMessage();
    private Position pos;

    @Before
    public void init() {
        pos = new Position(1, 1);
    }

    @Test
    public void actionMessageTest() {
        Position wPos = new Position(1, 1);
        List<Position> positions = new ArrayList<>(List.of(new Position(2, 2)));
        ActionMessage msg = new ActionMessage(PlayerIndex.PLAYER0, wPos, positions, ActionType.MOVE);

        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.UP_A, stub.n);
        assertEquals(wPos, msg.getWorkerPos());
        assertEquals(positions, msg.getPossiblePosition());
        assertEquals(ActionType.MOVE, msg.getActionType());
    }

    @Test
    public void buildMessageTest() {

        BuildMessage msg = new BuildMessage(PlayerIndex.PLAYER0, pos);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.BUILD, stub.n);
        assertEquals(pos, msg.getBuildPosition());
    }

    @Test
    public void closeConnectionMessageTest() {
        CloseConnectionMessage msg = new CloseConnectionMessage(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.C_CON, stub.n);
        assertEquals("Connection closed", msg.toString());
    }

    @Test
    public void nicknameMessageTest() {
        NicknameMessage msg = new NicknameMessage(PlayerIndex.PLAYER0, "W");
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.NICK, stub.n);
        assertEquals("W", msg.getNickname());
    }

    @After
    public void delete() {
        stub = null;
        pos = null;
    }

}
