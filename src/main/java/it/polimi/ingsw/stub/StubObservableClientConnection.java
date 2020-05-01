package it.polimi.ingsw.stub;

import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class StubObservableClientConnection extends Observable<Message> implements ClientConnection, Observer {

    private List<Message> mesRemoteToView;

    public StubObservableClientConnection(Message m) {
        mesRemoteToView = new ArrayList<>();
        mesRemoteToView.add(m);
        notify(m);
    }

    @Override
    public void closeConnection() {

    }

    //remote->view
    @Override
    public void asyncSend(Message message) {
        mesRemoteToView.add(message);
    }

    //->controller
    public void setMsg(Message message){
        this.mesRemoteToView.clear();
        notify(message);
    }

    public List<Message> getMesRemoteToView(){
        return this.mesRemoteToView;
    }

    @Override
    public void update(Object message) {
        asyncSend((Message) message);
    }
}
