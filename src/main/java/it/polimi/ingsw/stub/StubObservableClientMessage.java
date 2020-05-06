package it.polimi.ingsw.stub;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.Message;

public class StubObservableClientMessage {

    private Client client;
    private Message mes;

    public StubObservableClientMessage(Message m, Client c){
        this.client = c;
        this.mes = m;
    }

    public void sendFromClientMessage(){
        client.sendToServer(mes);
    }

    public void setMessageToSend(Message mes){
        this.mes = mes;
    }


}
