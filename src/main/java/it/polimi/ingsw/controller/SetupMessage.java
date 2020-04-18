package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidCommunicatorStringException;
import it.polimi.ingsw.exception.InvalidTypeMessage;

import java.util.List;

/**
 * LobbyMessage extends Message and represent an exchanged Message when the game is in setup state
 */
public class SetupMessage extends Message {

    public SetupMessage(String sender, String receiver, TypeMessage type, String content) throws NullPointerException, InvalidCommunicatorStringException, InvalidTypeMessage {
        super(sender, receiver, type, content, List.of(TypeMessage.GODLIKE_CHOOSE_CARDS, TypeMessage.SELECT_CARD));
    }

}
