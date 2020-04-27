package it.polimi.ingsw.network;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<Message> observer);

    void asyncSend(Message message);
}