package it.polimi.ingsw.network;

import it.polimi.ingsw.Client.ClientManager;
import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Client implements ServerConnection {

    private final ClientManager clientManager;
    private final ClientModel clientModel;
    private final ClientView clientView;
    private final String ip;
    private final int port;
    private Message message;
    private transient Timer pingTimer;
    private transient final BlockingQueue<MessageToClient> inputMessageQueue = new ArrayBlockingQueue<>(10);
    private transient final BlockingQueue<MessageToServer> outputMessageQueue = new ArrayBlockingQueue<>(10);
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Thread threadWrite;
    private boolean active = true;


    static final int DISCONNECTION_TIME = 10000;

    public Client(String ip, int port, String typeView) {

        this.ip = ip;
        this.port = port;
        this.message = null;
        this.pingTimer = new Timer();
        this.clientModel = new ClientModel();
        this.clientManager = new ClientManager(this, this.clientModel);
        this.clientView = new GUI(this.clientModel);
        this.clientView.addObserver(this.clientManager);
        this.clientModel.addObserver(this.clientView);
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * @param socketIn Input stream used to receive object
     * @return thread
     * Used to read message input and adding them to list
     */
    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    MessageToClient inputMessage = (MessageToClient) socketIn.readObject();
                    if (inputMessage != null && inputMessage.getType() == TypeMessage.PING) {
                        System.out.println("PING");
                        outputMessageQueue.put(new PongMessage());
                        pingTimer.cancel();
                        pingTimer = new Timer();
                        pingTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                disconnect();
                            }
                        }, DISCONNECTION_TIME);
                    } else if (inputMessage != null && inputMessage.getType() != TypeMessage.PING) {
                        inputMessageQueue.put(inputMessage);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (InterruptedException | IOException | ClassNotFoundException e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * @param socketOut Output stream use to write object on socket
     * @return thread
     * Used to write message on socket to client
     */
    public Thread asyncWriteToSocket(final ObjectOutputStream socketOut){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    socketOut.reset();
                    socketOut.writeObject(outputMessageQueue.take());
                    socketOut.flush();
                }
            } catch (IOException | InterruptedException e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread clientManageReadMessage() {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    clientManager.updateClient(inputMessageQueue.take());
                }
            } catch (InterruptedException e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }


    /**
     * @throws IOException Create the socket, the output and input stream and run the threads of writing and reading on socket
     */
    public void run() throws IOException {
        this.clientView.init();
        socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        this.clientView.init();
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            threadWrite = asyncWriteToSocket(socketOut);
            Thread threadController = clientManageReadMessage();
            t0.join();
            threadWrite.join();
            threadController.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
            Logger.getAnonymousLogger().severe(e.getMessage());
            disconnect();
        }
    }

    public void close() throws IOException {
        if(!socket.isClosed()){
            System.out.println("Client is closed");
            socketOut.close();
            socketIn.close();
            socket.close();
        }
        socketIn = null;
        socketOut = null;

    }

    public void disconnect() {
        try {
            close();
        } catch (IOException e) {
            System.err.println("Error during disconnection of client");
            Logger.getAnonymousLogger().severe(e.getMessage());

        }
    }

    @Override
    public void sendToServer(MessageToServer message) {
        try {
            outputMessageQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
            setActive(false);
        }
    }
}