package it.polimi.ingsw.message;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
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
    public void buildPowerMessageTest() {

        BuildPowerMessage msg = new BuildPowerMessage(PlayerIndex.PLAYER0, pos, BuildType.LEVEL);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.UP_BP, stub.n);
        assertEquals(pos, msg.getBuildPosition());
        assertEquals(BuildType.LEVEL, msg.getBuildType());

        try {
            new BuildPowerMessage(PlayerIndex.PLAYER0, null, BuildType.LEVEL);
        } catch (NullPointerException e) {
            assertEquals("buildPos", e.getMessage());
        }

    }

    @Test
    public void closeConnectionMessageTest() {
        CloseConnectionMessage msg = new CloseConnectionMessage(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.C_CON, stub.n);
    }

    @Test
    public void connectionPlayerIndexMessageTest() {
        ConnectionPlayerIndex msg = new ConnectionPlayerIndex(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.UP_I, stub.n);
        assertEquals(PlayerIndex.PLAYER0, msg.getPlayerIndex());
    }

    @Test
    public void currentPlayerMessageTest() {
        CurrentPlayerMessage msg = new CurrentPlayerMessage(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.UP_CUR_P, stub.n);
        assertEquals(PlayerIndex.PLAYER0, msg.getCurrentPlayerIndex());
    }

    @Test
    public void godLikeChoseMessageTest() {
        GodLikeChoseMessage msg = new GodLikeChoseMessage(
                PlayerIndex.PLAYER0, new ArrayList<>(List.of("Apollo"))
        );
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.GL_CARD, stub.n);
        new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER1).execute(stub);
    }

    @Test
    public void informationMessageTest() {
        InformationMessage msg = new InformationMessage(
                PlayerIndex.PLAYER0, TypeMessage.BUILD, TypeMessage.NICKNAME, "W"
        );
        msg.execute(stub);
        assertEquals(TypeMessage.NICKNAME, msg.getSpecificType());

        try {
            new InformationMessage(PlayerIndex.PLAYER0, TypeMessage.BUILD, TypeMessage.NICKNAME, null);
        } catch (NullPointerException e) {
            assertEquals("stringMessage", e.getMessage());
        }
    }

    @Test
    public void loserMessageTest() {
        LoserMessage msg = new LoserMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER1);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.UP_L, stub.n);
        assertEquals(PlayerIndex.PLAYER1, msg.getLoserPlayer());
    }

    @Test
    public void moveMessageTest() {

        MoveMessage msg = new MoveMessage(PlayerIndex.PLAYER0, pos, new Position(0, 0));
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.MOVE, stub.n);
    }


    @Test
    public void nicknameMessageTest() {
        NicknameMessage msg = new NicknameMessage(PlayerIndex.PLAYER0, "W");
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.NICK, stub.n);
        assertEquals("W", msg.getNickname());
    }

    @Test
    public void pingMessageTest() {
        PingMessage msg = new PingMessage(PlayerIndex.PLAYER0);
        msg.execute(stub);
        assertEquals("PingMessage:senderPlayer: PLAYER0content: PING", msg.toString());
    }

    @Test
    public void playerSelectGodMessageTest() {
        PlayerSelectGodMessage msg = new PlayerSelectGodMessage(PlayerIndex.PLAYER0, "Apollo");
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.SEL_C, stub.n);
    }

    @Test
    public void putWorkerMessageTest() {
        PutWorkerMessage msg = new PutWorkerMessage(PlayerIndex.PLAYER0, pos, new Position(2, 2));
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.PUT, stub.n);
    }

    @Test
    public void updateStateMessageTest() {
        UpdateStateMessage msg = new UpdateStateMessage(PlayerIndex.PLAYER0, GameState.MOVE);
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.UP_S, stub.n);
        assertEquals(GameState.MOVE, msg.getGameState());
    }

    @Test
    public void errorMessageTest() {
        ErrorMessage msg = new ErrorMessage(
                PlayerIndex.PLAYER0, TypeMessage.NICKNAME, "W"
        );
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.INF, stub.n);
    }

    @Test
    public void okMessageTest() {
        OkMessage msg = new OkMessage(
                PlayerIndex.PLAYER0, TypeMessage.NICKNAME, "W"
        );
        msg.execute(stub);
        assertEquals(StubControllableByServerMessage.INF, stub.n);
    }

    @After
    public void delete() {
        stub = null;
        pos = null;
    }

}
