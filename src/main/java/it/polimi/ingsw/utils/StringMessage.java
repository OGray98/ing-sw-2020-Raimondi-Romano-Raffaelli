package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class StringMessage extends Message {

    private final String stringMessage;

    public StringMessage(PlayerIndex client, TypeMessage type, String stringMessage) {
        super(client, type);
        if (stringMessage == null)
            throw new NullPointerException("stringMessage");
        this.stringMessage = stringMessage;
    }

    public String getString() {
        return this.stringMessage;
    }
}