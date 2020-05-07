package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class CloseConnectionMessage extends Message {

    public CloseConnectionMessage(PlayerIndex index) {
        super(index, TypeMessage.CLOSE_CONNECTION);
    }

    @Override
    public String toString() {
        return "Connection closed";
    }
}
