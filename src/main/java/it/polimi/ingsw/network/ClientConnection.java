package it.polimi.ingsw.network;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;

import java.io.IOException;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<Message> observer);

    void asyncSend(Message message);

    void setClientIndex(PlayerIndex clientIndex);

    boolean isConnected();

    void ping(PlayerIndex player) throws IOException;

    void forceDisconnection();
}