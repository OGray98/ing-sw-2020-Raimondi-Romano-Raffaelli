package it.polimi.ingsw;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;

    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
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
        while(waitingConnection.size() == 2){
            List<String> keys = new ArrayList<>(waitingConnection.keySet());
            ClientConnection c1 = waitingConnection.get(keys.get(0));
            ClientConnection c2 = waitingConnection.get(keys.get(1));
            // TODO : è da completare!!!
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
                System.out.println("Client is connected");
                SocketClientConnection socketClientConnection = new SocketClientConnection(socket,this);
                executor.submit(socketClientConnection);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
