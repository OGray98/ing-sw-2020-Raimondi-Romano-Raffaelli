package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Messages that implements this interface are message that will be sent to the view
 * */
public interface MessageToView {

    /**
     * Method that implements the Command pattern for the messages
     * when a ControllableByViewMessage receives a message it will call this method
     * @param controllable is the object that will receive the message
     * @throws NullPointerException if controllable is null
     * */
    void execute(ControllableByViewMessage controllable) throws NullPointerException;

    /**
     * @return the type of the message
     * */
    TypeMessage getType();

    /**
     * @return the index of the player who sent this message
     * */
    PlayerIndex getClient();
}
