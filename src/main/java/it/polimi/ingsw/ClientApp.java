package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;

import java.io.IOException;

/**
 * Main class that starts the client and allows users to play after the server is launched
 * */
public class ClientApp {

    public final static String GUI = "g";
    public final static String CLI = "c";

    public static void main(String[] args) {
        try {

            String type = "";

            if (args.length == 0)
                type = GUI;
            else if (args.length == 1) {
                if (args[0].equals("-c"))
                    type = CLI;
                else
                    System.out.println("Argument acceptable : -c");
            } else
                System.out.println("Argument acceptable : -c");

            if (type.equals(GUI) || type.equals(CLI)) {
                Client client = new Client(type);
                client.run();
            }
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}