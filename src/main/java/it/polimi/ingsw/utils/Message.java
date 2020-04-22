package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message class is an abstract class which represent an abstraction of exchanged messages between server and client
 */
public abstract class Message {

    private final PlayerIndex client;
    private final TypeMessage type;

    public Message(PlayerIndex client, TypeMessage type) {
        this.client = client;
        this.type = type;
    }

    public PlayerIndex getClient() {
        return client;
    }

    public TypeMessage getType() {
        return type;
    }
}
