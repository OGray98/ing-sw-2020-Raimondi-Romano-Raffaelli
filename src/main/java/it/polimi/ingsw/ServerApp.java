package it.polimi.ingsw;


import it.polimi.ingsw.network.Server;

import java.io.IOException;

public class ServerApp {

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.run();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}