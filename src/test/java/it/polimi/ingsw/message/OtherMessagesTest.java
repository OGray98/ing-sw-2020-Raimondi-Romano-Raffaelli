package it.polimi.ingsw.message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OtherMessagesTest {

    @Test
    public void pongMessageTest() {
        PongMessage pong = new PongMessage();
        pong.execute(new StubControllableByClientMessage());
        assertEquals("Pong message from client", pong.toString());
    }

}
