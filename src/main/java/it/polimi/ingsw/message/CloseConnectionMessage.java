package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message used to notify a player disconnected
 * */
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
