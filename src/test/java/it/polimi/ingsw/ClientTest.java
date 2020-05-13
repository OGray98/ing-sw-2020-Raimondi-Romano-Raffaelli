package it.polimi.ingsw;


import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.stub.StubObservableClientMessage;

import java.io.IOException;

public class ClientTest{


    public static void main(String[] args) {
        StubObservableClientMessage clientMessage;
        Client client = new Client("127.0.0.1", 12345);
        //clientMessage = new StubObservableClientMessage(new NicknameMessage(PlayerIndex.PLAYER0,"Rock"),client);
        // clientMessage.sendFromClientMessage();
        try {
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}