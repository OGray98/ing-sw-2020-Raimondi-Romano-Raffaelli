package it.polimi.ingsw.network;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;

    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(12);
    private Map<String, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();


    public synchronized void deleteClient(ClientConnection c){
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null){
            opponent.closeConnection();
        }
        playingConnection.remove(opponent);
        playingConnection.remove(c);
        Iterator<String> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    public synchronized void lobby(ClientConnection c,String name){
        waitingConnection.put(name,c);
        while(waitingConnection.size() == 1){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            //ClientConnection c2 = waitingConnection.get(keys.get(1));
            //View player1 = new RemoteView(PlayerIndex.PLAYER0,"Marco",c1);
            //View player2 = new RemoteView(PlayerIndex.PLAYER1,keys.get(0),c2);
            break;
        }
    }


    public Server() throws IOException{
        this.serverSocket = new ServerSocket(PORT);
        System.out.println("Port is open ");
    }

    public void run(){
        while(true){
            try {
                Socket socket = serverSocket.accept();
                //System.out.println("Client is connected");
                SocketClientConnection socketClientConnection = new SocketClientConnection(socket,this);
                executor.submit(socketClientConnection);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
