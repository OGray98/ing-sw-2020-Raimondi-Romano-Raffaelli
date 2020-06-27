package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Messages that implements this interface are message that will be sent to the client
 * */
public interface MessageToClient {

    /**
     * Method that implements the Command pattern for the messages
     * when a ControllableByServerMessage receives a message it will call this method
     * @param controllable is the object that will receive the message
     * @throws NullPointerException if controllable is null
     * */
    void execute(ControllableByServerMessage controllable) throws NullPointerException;

    /**
     * @return the type of the message
     * */
    TypeMessage getType();

    /**
     * @return the index of the player who sent this message
     * */
    PlayerIndex getClient();
}
