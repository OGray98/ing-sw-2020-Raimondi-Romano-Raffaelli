package it.polimi.ingsw;


import it.polimi.ingsw.network.Client;

import java.io.IOException;

public class ClientTest {

    public static void main(String args[]){

        Client client = new Client("127.0.0.1",12345);
        try{
            client.run();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}