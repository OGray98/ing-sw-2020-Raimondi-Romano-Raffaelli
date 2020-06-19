package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message sent from server to client to see if the client is still connected
 */
public class PingMessage extends Message implements MessageToClient {

    public PingMessage(PlayerIndex player) {
        super(player, TypeMessage.PING);
    }

    @Override
    public String toString() {
        return "PingMessage:" +
                "senderPlayer: " + getClient() +
                "content: " + getType();
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {

    }
}
