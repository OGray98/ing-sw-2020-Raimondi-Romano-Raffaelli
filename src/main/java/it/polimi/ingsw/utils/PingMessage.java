package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class PingMessage extends Message{

    public PingMessage(PlayerIndex player){
        super(player,TypeMessage.PING);
    }

    @Override
    public String toString() {
        return "PingMessage:" +
                "senderPlayer: " + getClient() +
                "content: " + getType();
    }
}
