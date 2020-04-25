package it.polimi.ingsw;

import it.polimi.ingsw.observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<String> observer);

    void asyncSend(Object message);
}
