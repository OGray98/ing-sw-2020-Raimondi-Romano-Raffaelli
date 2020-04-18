package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidCommunicatorStringException;
import it.polimi.ingsw.exception.InvalidTypeMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    private static Message lobbyMessage;
    private static Message setupMessage;
    private static String sender = "PLAYER0";
    private static String receiver = "SERVER";
    private TypeMessage typeMsgLobby = TypeMessage.NICKNAME;
    private TypeMessage typeMsgSetup = TypeMessage.GODLIKE_CHOOSE_CARDS;
    private String contentMsgLobby = "Jack";
    private String contentMsgSetup = "Apollo";

    @Before
    public void setUp() {
        lobbyMessage = new LobbyMessage(sender, receiver, typeMsgLobby, contentMsgLobby);
        setupMessage = new SetupMessage(sender, receiver, typeMsgSetup, contentMsgSetup);
    }

    @Test
    public void constructor() {
        try {
            new LobbyMessage(null, receiver, typeMsgLobby, contentMsgLobby);
        } catch (NullPointerException e) {
            assertEquals("sender", e.getMessage());
        }
        try {
            new LobbyMessage(sender, null, typeMsgLobby, contentMsgLobby);
        } catch (NullPointerException e) {
            assertEquals("receiver", e.getMessage());
        }
        try {
            new LobbyMessage(sender, receiver, typeMsgLobby, null);
        } catch (NullPointerException e) {
            assertEquals("content", e.getMessage());
        }
        try {
            new LobbyMessage("PLAYER4", receiver, typeMsgLobby, contentMsgLobby);
        } catch (InvalidCommunicatorStringException e) {
            assertEquals("You can't have a sender named PLAYER4", e.getMessage());
        }
        try {
            new LobbyMessage(sender, "PLAYER4", typeMsgLobby, contentMsgLobby);
        } catch (InvalidCommunicatorStringException e) {
            assertEquals("You can't have a receiver named PLAYER4", e.getMessage());
        }
        try {
            new LobbyMessage(sender, receiver, typeMsgSetup, contentMsgLobby);
        } catch (InvalidTypeMessage e) {
            assertEquals("A it.polimi.ingsw.controller.LobbyMessage can't be of type: " + typeMsgSetup, e.getMessage());
        }

    }

    @Test
    public void getSender() {
        assertEquals(sender, lobbyMessage.getSender());
        assertEquals(sender, setupMessage.getSender());
    }

    @Test
    public void getReceiver() {
        assertEquals(receiver, lobbyMessage.getReceiver());
        assertEquals(receiver, setupMessage.getReceiver());
    }

    @Test
    public void getType() {
        assertEquals(typeMsgLobby, lobbyMessage.getType());
        assertEquals(typeMsgSetup, setupMessage.getType());
    }

    @Test
    public void getContent() {
        assertEquals(contentMsgLobby, lobbyMessage.getContent());
        assertEquals(contentMsgSetup, setupMessage.getContent());
    }

    @Test
    public void testToString() {
        assertEquals("Message{type=" + typeMsgLobby + ", receiver=" + receiver + ", content=" + contentMsgLobby + '}',
                lobbyMessage.toString());
    }
}