package it.polimi.ingsw.message;

import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MessageToServerTest {

    private StubControllableByClientMessage stub = new StubControllableByClientMessage();
    private Position pos;

    @Before
    public void init() {
        pos = new Position(1, 1);
    }

    @Test
    public void buildMessageTest() {

        BuildMessage msg = new BuildMessage(PlayerIndex.PLAYER0, pos);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.BUILD, stub.n);
        assertEquals(pos, msg.getBuildPosition());
    }

    @Test
    public void closeConnectionMessageTest() {
        CloseConnectionMessage msg = new CloseConnectionMessage(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.C_CON, stub.n);
        assertEquals("Connection closed", msg.toString());
    }

    @Test
    public void endTurnMessageTest() {
        EndTurnMessage msg = new EndTurnMessage(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.E_TURN, stub.n);
    }

    @Test
    public void godLikeChoosePlayerMessageTest() {
        GodLikeChooseFirstPlayerMessage msg = new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER1);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.GL_P, stub.n);
        assertEquals(PlayerIndex.PLAYER1, msg.getPlayerFirst());
    }

    @Test
    public void godLikeChooseMessageTest() {
        GodLikeChoseMessage msg = new GodLikeChoseMessage(PlayerIndex.PLAYER0, new ArrayList<>(List.of("Apollo")));
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.GL_CARD, stub.n);
        assertEquals("Apollo", msg.getGodNames().get(0));
        assertEquals(1, msg.getGodNames().size());

        try {
            new GodLikeChoseMessage(PlayerIndex.PLAYER0, new ArrayList<>(List.of("Ryu")));
        } catch (WrongGodNameException e) {
            assertEquals(new WrongGodNameException("godNames").getMessage(), e.getMessage());
        }

        try {
            new GodLikeChoseMessage(PlayerIndex.PLAYER0, null);
        } catch (NullPointerException e) {
            assertEquals("godNames", e.getMessage());
        }
    }

    @Test
    public void moveMessageTest() {

        Position pos2 = new Position(2, 2);
        MoveMessage msg = new MoveMessage(PlayerIndex.PLAYER0, pos, pos2);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.MOVE, stub.n);
        assertEquals(pos, msg.getWorkerPosition());
        assertEquals(pos2, msg.getMovePosition());

        try {
            new MoveMessage(PlayerIndex.PLAYER0, null, pos);
        } catch (NullPointerException e) {
            assertEquals("pos1", e.getMessage());
        }

        try {
            new MoveMessage(PlayerIndex.PLAYER0, pos, null);
        } catch (NullPointerException e) {
            assertEquals("pos2", e.getMessage());
        }

    }

    @Test
    public void nicknameMessageTest() {
        NicknameMessage msg = new NicknameMessage(PlayerIndex.PLAYER0, "W");
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.NICK, stub.n);
        assertEquals("W", msg.getNickname());
    }

    @Test
    public void playerSelGodMessageTest() {
        PlayerSelectGodMessage msg = new PlayerSelectGodMessage(PlayerIndex.PLAYER0, "Apollo");
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.SEL_C, stub.n);
        assertEquals("Apollo", msg.getGodName());

        try {
            new PlayerSelectGodMessage(PlayerIndex.PLAYER0, "Y");
        } catch (WrongGodNameException e) {
            assertEquals(new WrongGodNameException("Y").getMessage(), e.getMessage());
        }
    }

    @Test
    public void putWorkerMessageTest() {
        Position pos2 = new Position(2, 2);
        PutWorkerMessage msg = new PutWorkerMessage(PlayerIndex.PLAYER0, pos, pos2);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.PUT, stub.n);
        assertEquals(pos, msg.getPositionOne());
        assertEquals(pos2, msg.getPositionTwo());
    }

    @Test
    public void typeMatchMessageTest() {
        TypeMatchMessage msg = new TypeMatchMessage(PlayerIndex.PLAYER0, true);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.THREE_P, stub.n);
        assertTrue(msg.isThreePlayersMatch());
    }

    @Test
    public void usePowerMessageTest() {
        Position pos2 = new Position(2, 2);
        UsePowerMessage msg = new UsePowerMessage(PlayerIndex.PLAYER0, pos, pos2);
        msg.execute(stub);
        assertEquals(StubControllableByClientMessage.USE_PO, stub.n);
        assertEquals(pos, msg.getWorkerPosition());
        assertEquals(pos2, msg.getPowerPosition());

    }

    @After
    public void delete() {
        stub = null;
        pos = null;
    }

}
