package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;

import javax.swing.*;
import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {
        try {
            String ip = JOptionPane.showInputDialog("Insert server ip:");
            String portString = JOptionPane.showInputDialog("Insert port:");
            int port = Integer.parseInt(portString);
            Client client = new Client(ip, port, "GUI");
            client.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}