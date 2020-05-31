package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class CloseConnectionMessage extends Message implements MessageToServer, MessageToClient {

    public CloseConnectionMessage(PlayerIndex index) {
        super(index, TypeMessage.CLOSE_CONNECTION);
    }

    @Override
    public String toString() {
        return "Connection closed";
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        controllable.handleCloseConnectionMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        controllable.updateCloseConnectionMessage(this);
    }
}
