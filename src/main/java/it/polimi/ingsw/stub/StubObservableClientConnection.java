package it.polimi.ingsw.stub;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class StubObservableClientConnection extends Observable<Message> implements ClientConnection {

    private List<Message> mes;

    public StubObservableClientConnection(Message m) {
        mes = new ArrayList<>();
        mes.add(m);
        notify(m);
    }

    @Override
    public void closeConnection() {

    }

    //remote->view
    @Override
    public void asyncSend(Message message) {
        mes.add(message);
    }

    //->controller
    public void setMsg(Message message){
        this.mes.clear();
        notify(message);
    }
}
