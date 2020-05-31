package it.polimi.ingsw.network;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;


public class SocketClientConnection extends Observable<MessageToServer> implements ClientConnection, Runnable {

    private ObjectOutputStream out;
    private final Socket socket;
    private final Server server;
    private boolean active = true;
    private ObjectInputStream in;

    private transient final BlockingQueue<MessageToServer> inputMessageQueue = new ArrayBlockingQueue<>(10);
    private PlayerIndex clientIndex;

    public SocketClientConnection(Socket socket, Server server) {
        this.server = server;
        this.socket = socket;
    }

    private synchronized void setIsActiveFalse() {
        this.active = false;
    }

    @Override
    public synchronized boolean isConnected() {
        return active;
    }


    /**
     * @param message to write on socket server -> client
     */
    private synchronized void send(MessageToClient message) throws IOException {
        out.reset();
        out.writeObject(message);
        out.flush();
    }


    public synchronized void closeConnection() {

        notify(new CloseConnectionMessage(this.clientIndex));

        try {
            out.close();
            in.close();
            socket.close();
            System.out.println("Socket connection closed");
        } catch (IOException e) {
            System.err.println("Error closing socket client connection");
            Logger.getAnonymousLogger().severe(e.getMessage());

        }
        setIsActiveFalse();
    }

    @Override
    public void asyncSend(final MessageToClient message) {
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
                    while (isConnected()) {
                        notify(inputMessageQueue.take());
                    }
                } catch (InterruptedException e) {
                    setIsActiveFalse();
                }
            }).start();
            while (isConnected()) {
                //if (in.available() != 0) {
                try {
                    MessageToServer inputMessage = (MessageToServer) in.readObject();
                    if (inputMessage != null && inputMessage.getType() != TypeMessage.PONG) {
                        try {
                            inputMessageQueue.put(inputMessage);
                        } catch (InterruptedException e) {
                            System.err.println("Error!" + e.getMessage());
                            Logger.getAnonymousLogger().severe(e.getMessage());
                            setIsActiveFalse();
                        }
                    }
                } catch (ClassNotFoundException e) {
                    Logger.getAnonymousLogger().severe(e.getMessage());
                    setIsActiveFalse();
                }
            }
            //}

        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error! " + e.toString());
            Logger.getAnonymousLogger().severe(e.getMessage());
            setIsActiveFalse();
        }
    }

    @Override
    public void ping(PlayerIndex player) throws IOException {
        send(new PingMessage(player));
    }

    @Override
    public void forceDisconnection() {
        notify(new CloseConnectionMessage(this.clientIndex));
        setIsActiveFalse();
    }

    public void setClientIndex(PlayerIndex clientIndex) {
        this.clientIndex = clientIndex;
    }
}