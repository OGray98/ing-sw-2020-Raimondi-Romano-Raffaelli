package it.polimi.ingsw.message;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public interface MessageToServer {
    void execute(ControllableByClientMessage controllable) throws NullPointerException;

    PlayerIndex getClient();

    TypeMessage getType();
}
