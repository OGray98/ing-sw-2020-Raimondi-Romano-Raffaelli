package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.MessageToClient;
import it.polimi.ingsw.utils.MessageToServer;

import java.util.ArrayList;
import java.util.List;

public class StubObservableClientConnection extends Observable<MessageToServer> implements ClientConnection, Observer<MessageToClient> {

    private final List<MessageToClient> mesRemoteToView;

    public StubObservableClientConnection(MessageToServer m) {
        mesRemoteToView = new ArrayList<>();
        notify(m);
    }

    public MessageToClient getLastMessage() {
        return mesRemoteToView.get(mesRemoteToView.size() - 1);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void ping(PlayerIndex player) {

    }

    @Override
    public void forceDisconnection() {

    }

    @Override
    public void closeConnection() {

    }

    //remote->view
    @Override
    public void asyncSend(MessageToClient message) {
        mesRemoteToView.add(message);
    }

    @Override
    public void setClientIndex(PlayerIndex clientIndex) {

    }

    //->controller
    public void setMsg(MessageToServer message) {
        this.mesRemoteToView.clear();
        notify(message);
    }

    public List<MessageToClient> getMesRemoteToView() {
        return this.mesRemoteToView;
    }

    @Override
    public void update(MessageToClient message) {
        asyncSend(message);
    }
}
