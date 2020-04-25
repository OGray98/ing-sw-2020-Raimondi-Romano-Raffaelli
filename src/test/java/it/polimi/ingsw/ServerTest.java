package it.polimi.ingsw;


import java.io.IOException;

public class ServerTest {

    public static void main(String args[])  {
        Server server;
        try{
            server = new Server();
            server.run();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}