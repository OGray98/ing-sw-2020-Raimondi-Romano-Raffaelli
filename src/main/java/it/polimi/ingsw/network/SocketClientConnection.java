package it.polimi.ingsw.network;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.CloseConnectionMessage;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.PingMessage;
import it.polimi.ingsw.utils.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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

    private transient final BlockingQueue<Message> inputMessageQueue = new ArrayBlockingQueue<>(10);


    public SocketClientConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
        this.pingTimer = new Timer();
    }


    private synchronized boolean isActive() {
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
    private synchronized void send(Message message) throws IOException {
        out.reset();
        out.writeObject(message);
        out.flush();
    }




    public synchronized void closeConnection() {

        notify(new CloseConnectionMessage(PlayerIndex.PLAYER0));

        try {
            out.close();
            in.close();
            socket.close();
            System.out.println("Socket connection closed");
        } catch (IOException e) {
            System.err.println("Error closing socket client connection");
            Logger.getAnonymousLogger().severe(e.getMessage());

        }
        active = false;
        connectionActive = false;
        setCloseForced(false);
    }

    private void close(){
        closeConnection();
        //server.deleteClient(this);
    }



    public void asyncSend(final Message message) {
        new Thread(() -> {
            try {
                send(message);
            } catch (IOException e) {
                closeConnection();
            }
        }).start();
    }

    /**
     * Create the input and output stream, insert the connection in server lobby and remain active to read message input
     * from clients
     */
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            server.lobby(this);
            new Thread(() -> {
                try {
                    while (isActive()) {
                        notify(inputMessageQueue.take());
                    }
                } catch (InterruptedException e) {
                    active = false;
                }
            }).start();
            while (isActive()) {
                try {
                    Message inputMessage = (Message) in.readObject();
                    /*if (inputMessage != null && inputMessage.getType() == TypeMessage.PONG) {
                        this.pingTimer.cancel();
                        this.pingTimer = new Timer();
                        this.pingTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!getCloseForced())
                                    return;
                                setCloseForced(false);
                                close();
                            }
                        }, DISCONNECTION_TIME);
                    } else*/
                    if (inputMessage != null && inputMessage.getType() != TypeMessage.PONG) {
                        try {
                            inputMessageQueue.put(inputMessage);
                        } catch (InterruptedException e) {
                            System.err.println("Error!" + e.getMessage());
                            Logger.getAnonymousLogger().severe(e.getMessage());
                            connectionActive = false;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    Logger.getAnonymousLogger().severe(e.getMessage());
                    connectionActive = false;
                }
            }

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! oooo " + e.toString());
            Logger.getAnonymousLogger().severe(e.getMessage());
            connectionActive = false;
        }
    }

    @Override
    public void ping(PlayerIndex player) throws IOException {
        send(new PingMessage(player));
    }


}