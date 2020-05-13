package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public interface MessageToServer {
    void execute(ControllableByClientMessage controllable) throws NullPointerException;

    PlayerIndex getClient();

    TypeMessage getType();
}
