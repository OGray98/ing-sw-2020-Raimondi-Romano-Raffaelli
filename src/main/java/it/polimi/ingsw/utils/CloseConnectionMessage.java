package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class CloseConnectionMessage extends Message implements MessageToServer {

    public CloseConnectionMessage(PlayerIndex index) {
        super(index, TypeMessage.CLOSE_CONNECTION);
    }

    @Override
    public String toString() {
        return "Connection closed";
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {

    }
}
