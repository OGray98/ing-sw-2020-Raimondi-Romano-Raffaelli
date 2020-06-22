package it.polimi.ingsw.network;

import it.polimi.ingsw.message.MessageToClient;
import it.polimi.ingsw.message.MessageToServer;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<MessageToServer> observer);

    void asyncSend(MessageToClient message);

    void setClientIndex(PlayerIndex clientIndex);

    boolean isConnected();

    void ping(PlayerIndex player) throws IOException;

    void forceDisconnection();
}