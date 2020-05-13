package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.MessageToServer;

public interface ServerConnection {
    void sendToServer(MessageToServer message);
}
