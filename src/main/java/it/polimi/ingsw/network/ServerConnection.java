package it.polimi.ingsw.network;

import it.polimi.ingsw.message.MessageToServer;

public interface ServerConnection {
    void sendToServer(MessageToServer message);
}
