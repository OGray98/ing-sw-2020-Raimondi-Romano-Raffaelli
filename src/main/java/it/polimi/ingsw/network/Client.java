package it.polimi.ingsw.network;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.PingMessage;
import it.polimi.ingsw.utils.PongMessage;
import it.polimi.ingsw.utils.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

public class Client {

    private final String ip;
    private final int port;
    private Message message;
    private transient Timer pingTimer;
    private transient final List<Message> messageQueue;
    private  Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Thread threadWrite;


    static final int DISCONNECTION_TIME = 10000;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.message = null;
        this.pingTimer = new Timer();
        this.messageQueue = new ArrayList<>();

    }

    /**
     * @return List where message input are insert to read by client manager
     */
    public List<Message> getMessageQueue(){
        return messageQueue;
    }


    private boolean active = true;

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
                    Message inputMessage = (Message) socketIn.readObject();
                    if(inputMessage != null && inputMessage.getType() == TypeMessage.PING){
                        createClientMessage(new PongMessage());
                        pingTimer.cancel();
                        pingTimer = new Timer();
                        pingTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                disconnect();
                            }
                        }, DISCONNECTION_TIME);
                    } else if(inputMessage != null && inputMessage.getType() != TypeMessage.PING){
                        synchronized (messageQueue){
                            messageQueue.add(inputMessage);
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * @param socketOut Output stream use to write object on scoket
     * @return thread
     * Used to write message on socket to client
     */
    public Thread asyncWriteToSocket(final ObjectOutputStream socketOut){
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    if(getClientMessage() != null && getClientMessage().getType() == TypeMessage.PONG){
                    socketOut.reset();
                    socketOut.writeObject(getClientMessage());
                    socketOut.flush();
                    try{
                        threadWrite.sleep(1000);
                    } catch (InterruptedException e){
                        System.err.println("Error in pong response from client");
                        Logger.getAnonymousLogger().severe(e.getMessage());
                        threadWrite.interrupt();
                    }
                    }
                    else if(getClientMessage() != null && getClientMessage().getType() != TypeMessage.PONG){
                        socketOut.reset();
                        socketOut.writeObject(getClientMessage());
                        socketOut.flush();
                    }
                }
            }catch(Exception e){
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    /**
     * @param message to send to server
     */
    public void createClientMessage(Message message){
        this.message = message;
    }

    public Message getClientMessage(){
        return message;
    }


    /**
     * @throws IOException
     * Create the socket, the output and input stream and run the threads of writing and reading on socket
     */
    public void run() throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            threadWrite = asyncWriteToSocket(socketOut);
            t0.join();
            threadWrite.join();
        } catch(InterruptedException | NoSuchElementException e){
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

    public void disconnect(){
        try{
            close();
        }catch (IOException e){
            System.err.println("Error during disconnection of client");
            Logger.getAnonymousLogger().severe(e.getMessage());

        }
    }

}