package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * These messages will communicate from ClientModel to View, they are used to update the view
 * */
public interface MessageToView {
    void execute(ControllableByViewMessage controllable) throws NullPointerException;

    TypeMessage getType();

    PlayerIndex getClient();
}
