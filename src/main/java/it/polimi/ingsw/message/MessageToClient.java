package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Messages that implements this interface are message that will be sent to the client
 * */
public interface MessageToClient {
    void execute(ControllableByServerMessage controllable) throws NullPointerException;

    TypeMessage getType();

    PlayerIndex getClient();
}
