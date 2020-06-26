package it.polimi.ingsw.message;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Messages that implements this interface are message that will be sent to the server
 * */
public interface MessageToServer {
    void execute(ControllableByClientMessage controllable) throws NullPointerException;

    PlayerIndex getClient();

    TypeMessage getType();
}
