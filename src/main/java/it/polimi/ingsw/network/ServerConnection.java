package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.Message;

public interface ServerConnection {
    void sendToServer(Message message);
}
