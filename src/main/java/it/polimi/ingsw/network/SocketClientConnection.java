package it.polimi.ingsw.network;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.OkMessage;
import it.polimi.ingsw.utils.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketClientConnection extends Observable<Message> implements ClientConnection,Runnable{

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

    private synchronized void send(Message message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void closeConnection(){
        //send(new CloseConnectionMessage());
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

    public void asyncSend(final Message message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    @Override
    public void run() {
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            try {
                server.lobby(this);
                while (isActive()) {
                    Message read = (Message) in.readObject();
                    notify(read);
                }
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        }finally{
            close();
        }
    }


}