package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class Client {

    private final String ip;
    private final int port;
    private Message message;
    private transient Timer pingTimer;
    private transient List<Message> messageQueue;
    private transient Socket socket;
    private transient ObjectInputStream socketIn;
    private transient ObjectOutputStream socketOut;


    static final int DISCONNECTION_TIME = 15000;

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

    /**
     * @param mesQueue : List upgrade after client read a message
     */
    public void setMessageQueue(List<Message> mesQueue){
        this.messageQueue = mesQueue;
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
                        pingTimer.cancel();
                        pingTimer = new Timer();
                        pingTimer.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                disconnect();
                                //TODO: remove client from server list
                            }
                        },0, DISCONNECTION_TIME);
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
                    socketOut.reset();
                    socketOut.writeObject(getClientMessage());
                    socketOut.flush();
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
            Thread t1 = asyncWriteToSocket(socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
            disconnect();
        }
    }

    public void close() throws IOException {
        if(!socket.isClosed()){
            socket.close();
            socketOut.close();
            socketIn.close();
        }
        socketIn = null;
        socketOut = null;

    }

    public void disconnect(){
        try{
            close();
        }catch (IOException e){
            System.err.println("Error in closing socket");
            e.printStackTrace();
        }
    }

}