package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    public final static String GUI = "g";
    public final static String CLI = "c";

    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to use CLI or GUI?");
            System.out.println("Type \"c\" for CLI, \"g\" for GUI");

            String typed = scanner.next();
            while (!typed.equals(GUI) && !typed.equals(CLI)) {
                System.out.println("Type \"c\" for CLI, \"g\" for GUI");
                typed = scanner.next();
            }

            /*String ip = JOptionPane.showInputDialog("Insert server ip:");
            String portString = JOptionPane.showInputDialog("Insert port:");
            int port = Integer.parseInt(portString);*/

            Client client = new Client(typed);
            client.run();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}