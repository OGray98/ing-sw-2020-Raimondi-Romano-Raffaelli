package it.polimi.ingsw.network;

import it.polimi.ingsw.message.MessageToServer;

/**
 * This interface is used by client to send message to server
 */
public interface ServerConnection {
    void sendToServer(MessageToServer message);
}
