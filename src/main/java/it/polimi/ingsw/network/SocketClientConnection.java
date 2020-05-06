package it.polimi.ingsw.network;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


public class SocketClientConnection extends Observable<Message> implements ClientConnection,Runnable{

    private ObjectOutputStream out;
    private final Socket socket;
    private final Server server;
    private boolean active = true;
    private boolean connectionActive = true;
    private transient Timer pingTimer;
    static final int DISCONNECTION_TIME = 10000;
    private boolean closeForced = true;
    private ObjectInputStream in;


    public SocketClientConnection(Socket socket, Server server){
        this.server = server;
        this.socket = socket;
        this.pingTimer = new Timer();
    }


    private synchronized boolean isActive(){
        return active;
    }

    @Override
    public boolean isConnected() {
        return connectionActive;
    }

    private synchronized boolean getCloseForced(){
        return this.closeForced;
    }

    private void setCloseForced(boolean condition){
        this.closeForced = condition;
    }

    /**
     * @param message to write on socket server -> client
     */
    private synchronized void send(Message message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        }catch (IOException e){
            System.err.println("Error sending message to client");
            Logger.getAnonymousLogger().severe(e.getMessage());
            connectionActive = false;

        }
    }




    public synchronized void closeConnection(){
        if(getCloseForced()){
            notify(new CloseConnectionMessage());
            send(new CloseConnectionMessage());
        }
        try{
            out.close();
            in.close();
            socket.close();
            System.out.println("Socket connection closed");
        }catch (IOException e){
            System.err.println("Error closing socket client connection");
            Logger.getAnonymousLogger().severe(e.getMessage());

        }
        active = false;
        connectionActive = false;
        setCloseForced(false);
    }

    private void close(){
        closeConnection();
        server.deleteClient(this);
    }



    public void asyncSend(final Message message){
        new Thread(() -> send(message)).start();
    }

    /**
     * Create the input and output stream, insert the connection in server lobby and remain active to read message input
     * from clients
     */
    @Override
    public void run() {
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            try {
                server.lobby(this);
                while (isActive()) {
                    Message read = (Message) in.readObject();
                    if(read != null && read.getType() == TypeMessage.PONG){
                        this.pingTimer.cancel();
                        this.pingTimer = new Timer();
                        this.pingTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(!getCloseForced())
                                    return;
                                setCloseForced(false);
                                close();
                            }
                        },DISCONNECTION_TIME);
                    }
                    else if(read != null && read.getType() != TypeMessage.PONG){
                    notify(read);
                    }
                }
            }catch (ClassNotFoundException e){
                Logger.getAnonymousLogger().severe(e.getMessage());
                connectionActive = false;
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
            Logger.getAnonymousLogger().severe(e.getMessage());
            connectionActive = false;
        }
    }

    @Override
    public void ping(PlayerIndex player){
        send(new PingMessage(player));
    }


}