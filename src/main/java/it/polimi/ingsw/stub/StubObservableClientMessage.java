package it.polimi.ingsw.stub;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.MessageToServer;

public class StubObservableClientMessage {

    private Client client;
    private MessageToServer mes;

    public StubObservableClientMessage(MessageToServer m, Client c) {
        this.client = c;
        this.mes = m;
    }

    public void sendFromClientMessage(){
        client.sendToServer(mes);
    }

    public void setMessageToSend(MessageToServer mes) {
        this.mes = mes;
    }


}
