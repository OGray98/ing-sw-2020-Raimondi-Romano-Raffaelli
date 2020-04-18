package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidCommunicatorStringException;
import it.polimi.ingsw.exception.InvalidTypeMessage;

import java.util.List;


/**
 * LobbyMessage extends Message and represent an exchanged Message when players are connecting in the lobby
 */
public class LobbyMessage extends Message {

    public LobbyMessage(String sender, String receiver, TypeMessage type, String content) throws NullPointerException, InvalidCommunicatorStringException, InvalidTypeMessage {
        super(sender, receiver, type, content, List.of(TypeMessage.NICKNAME, TypeMessage.NUMBER_PLAYER_GAME));
    }


}
