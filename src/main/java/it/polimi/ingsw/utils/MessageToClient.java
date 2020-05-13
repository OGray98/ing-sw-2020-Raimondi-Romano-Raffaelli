package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public interface MessageToClient {
    void execute(ControllableByServerMessage controllable) throws NullPointerException;

    TypeMessage getType();

    PlayerIndex getClient();
}
