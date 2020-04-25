package it.polimi.ingsw;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable<String> implements ClientConnection,Runnable{

    private ObjectOutputStream out;
    private Socket socket;
    private Server server;
    private boolean active = true;


    public SocketClientConnection(Socket socket, Server server){
        this.server = server;
        this.socket = socket;
    }



    private synchronized boolean isActive(){
        return active;
    }

    private synchronized void send(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void closeConnection(){
        send("Connection closed");
        try{
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        active = false;
    }

    private void close(){
        closeConnection();
        server.deleteClient(this);
    }

    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        Scanner in;
        String name;
        try{
            in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            name = read;
            server.lobby(this, name);
            while(isActive()){
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }


}
