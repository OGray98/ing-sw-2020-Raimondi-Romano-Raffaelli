package it.polimi.ingsw.network;

import it.polimi.ingsw.Client.ClientManager;
import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.ClientApp;
import it.polimi.ingsw.ClientViewFactory.CLICreator;
import it.polimi.ingsw.ClientViewFactory.ClientViewCreator;
import it.polimi.ingsw.ClientViewFactory.GUICreator;
import it.polimi.ingsw.message.MessageToClient;
import it.polimi.ingsw.message.MessageToServer;
import it.polimi.ingsw.message.PongMessage;
import it.polimi.ingsw.message.TypeMessage;

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
    private final ClientView clientView;
    private static final int DISCONNECTION_TIME = 10000;
    private static final int TCP_SERVER_PORT = 4444;
    private transient Timer pingTimer;
    private transient final BlockingQueue<MessageToClient> inputMessageQueue = new ArrayBlockingQueue<>(10);
    private transient final BlockingQueue<MessageToServer> outputMessageQueue = new ArrayBlockingQueue<>(10);
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private boolean active = true;
    private String ip;

    public Client(String typeView) {

        this.pingTimer = new Timer();
        ClientModel clientModel = new ClientModel();
        this.clientManager = new ClientManager(this, clientModel);

        ClientViewCreator clientViewCreator;
        if (typeView.equals(ClientApp.GUI)) {
            clientViewCreator = new GUICreator();
        } else {
            clientViewCreator = new CLICreator();
        }
        this.clientView = clientViewCreator.createView(clientModel);

        this.clientManager.setClientView(this.clientView);

        this.clientView.addObserver(this.clientManager);
        clientModel.addObserver(this.clientView);
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
                        //System.out.println("PING");
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
                    MessageToServer msg = outputMessageQueue.take();
                    socketOut.writeObject(msg);
                    socketOut.flush();
                    /*if (msg.getType() != TypeMessage.PING && msg.getType() != TypeMessage.PONG)
                        System.out.println("Send message :" + msg.getType());*/
                }
            } catch (IOException | InterruptedException e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * @return thread that read message from queue and invoke client manager to change client action
     */
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
        this.ip = this.clientView.showSelectIP("Insert server IP:");

        while (!isServerAvailable()) {
            this.ip = this.clientView.showSelectIP(
                    "There isn't any available server on this IP, try with another IP:");
        }

        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());


        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread threadWrite = asyncWriteToSocket(socketOut);
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

    /**
     * @throws IOException Input-output exception
     * Close socket, input-stream and output-stream
     */
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
            clientView.showMessage("Server is offline!!");
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

    private boolean isServerAvailable() {
        if (ip == null) return false;
        try {
            socket = new Socket(this.ip, TCP_SERVER_PORT);
            return true;
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
        return false;
    }
}