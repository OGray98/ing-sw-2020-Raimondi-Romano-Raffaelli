package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public interface MessageToClient {
    void execute(ControllableByServerMessage controllable) throws NullPointerException;

    TypeMessage getType();

    PlayerIndex getClient();
}
