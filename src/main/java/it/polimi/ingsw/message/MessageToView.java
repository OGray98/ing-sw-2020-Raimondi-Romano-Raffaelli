package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Messages that implements this interface are message that will be sent to the view
 * */
public interface MessageToView {
    void execute(ControllableByViewMessage controllable) throws NullPointerException;

    TypeMessage getType();

    PlayerIndex getClient();
}
