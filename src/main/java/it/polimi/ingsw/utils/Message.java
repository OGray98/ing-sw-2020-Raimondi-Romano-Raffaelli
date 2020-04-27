package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

import java.io.Serializable;

/**
 * Message class is an abstract class which represent an abstraction of exchanged messages between server and client
 */
public abstract class Message implements Serializable {

    private static final long serialVersionUID = -1220918273625162876L;
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