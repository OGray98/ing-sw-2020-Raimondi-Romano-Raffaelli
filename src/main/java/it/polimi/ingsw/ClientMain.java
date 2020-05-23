package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        try {
            Client client = new Client("127.0.0.1", 1987, "GUI");
            client.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}