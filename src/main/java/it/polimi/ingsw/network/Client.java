package it.polimi.ingsw.network;

import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;

public class Client {

    private String ip;
    private int port;
    private Message message;
    private transient Timer pingTimer;

    static final int DISCONNECTION_TIME = 15000;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.message = null;
        this.pingTimer = new Timer();

    }

    private boolean active = true;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        Message inputMessage = (Message) socketIn.readObject();//TODO ricorda oggetti di tipo Message
                        if(inputMessage != null && inputMessage.getType() == TypeMessage.PING){
                            pingTimer.cancel();
                            pingTimer = new Timer();
                            pingTimer.schedule(/*close method*/,DISCONNECTION_TIME);
                        } else if(inputMessage != null && inputMessage.getType() != TypeMessage.PING){
                            //do something
                        }else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        socketOut.reset();
                        socketOut.writeObject(getClientMessage());
                        socketOut.flush();
                    }
                }catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void createClientMessage(Message message){
        this.message = message;
    }

    public Message getClientMessage(){
        return message;
    }


    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}