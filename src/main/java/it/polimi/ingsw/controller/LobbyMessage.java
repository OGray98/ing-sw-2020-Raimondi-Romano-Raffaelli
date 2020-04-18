package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.InvalidCommunicatorStringException;
import it.polimi.ingsw.exception.InvalidTypeMessage;

import java.util.List;

public class LobbyMessage extends Message {


    public LobbyMessage(String sender, String receiver, TypeMessage type, String content) throws NullPointerException, InvalidCommunicatorStringException, InvalidTypeMessage {
        super(sender, receiver, type, content, List.of(TypeMessage.NICKNAME, TypeMessage.NUMBER_PLAYER_GAME));
    }


}
