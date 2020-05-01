package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameManager;
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
    private ExecutorService executor = Executors.newFixedThreadPool(129);
    private Map<PlayerIndex, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private static int lobbyCount = 0;
    private GameManager controller = new GameManager();




    public synchronized void deleteClient(ClientConnection c){
        ClientConnection opponent = playingConnection.get(c);
        if(opponent != null){
            opponent.closeConnection();
        }
        playingConnection.remove(opponent);
        playingConnection.remove(c);
        Iterator<PlayerIndex> iterator = waitingConnection.keySet().iterator();
        while(iterator.hasNext()){
            if(waitingConnection.get(iterator.next())==c){
                iterator.remove();
            }
        }
    }

    public synchronized void lobby(ClientConnection c){
       if(lobbyCount == 0){
           waitingConnection.put(PlayerIndex.PLAYER0,c);
           ClientConnection c1 = waitingConnection.get(0);
           RemoteView player1View = new RemoteView(PlayerIndex.PLAYER0,c1);
           controller.addRemoteView(PlayerIndex.PLAYER0,player1View);
           lobbyCount++;
       }
       else if(lobbyCount != 0 && controller.getPlayerNum() == 2){
           waitingConnection.put(PlayerIndex.PLAYER1,c);
           if(waitingConnection.size() == 2){
               ClientConnection c2 = waitingConnection.get(1);
               RemoteView player2View = new RemoteView(PlayerIndex.PLAYER1,c2);
               controller.addRemoteView(PlayerIndex.PLAYER1,player2View);
               lobbyCount = 0;
           }
       }else if(lobbyCount != 0 && controller.getPlayerNum() == 3){
           waitingConnection.put(PlayerIndex.PLAYER1,c);
           waitingConnection.put(PlayerIndex.PLAYER2,c);
           if(waitingConnection.size() == 3){
               ClientConnection c2 = waitingConnection.get(1);
               ClientConnection c3 = waitingConnection.get(2);
               RemoteView player2View = new RemoteView(PlayerIndex.PLAYER1,c2);
               RemoteView player3View = new RemoteView(PlayerIndex.PLAYER2,c3);
               controller.addRemoteView(PlayerIndex.PLAYER1,player2View);
               controller.addRemoteView(PlayerIndex.PLAYER2,player3View);
               lobbyCount = 0;
           }

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
                System.err.println("Error during the open port on server");
                e.printStackTrace();
            }
        }
    }
}
