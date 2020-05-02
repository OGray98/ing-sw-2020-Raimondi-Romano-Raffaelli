package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Game;
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
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private Map<PlayerIndex, ClientConnection> waitingConnection = new HashMap<>();
    private Map<ClientConnection, ClientConnection> playingConnection = new HashMap<>();
    private static int lobbyCount = 0;
    private Game game = new Game();
    private GameManager controller = new GameManager(game);
    private Thread pingThread;


    /**
     * @param c connection to eliminate from the list of client player in lobby
     */
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

    /**
     * @param c connection to insert in lobby for the game
     */
    public synchronized void lobby(ClientConnection c) {

        if (lobbyCount == 0) {
            waitingConnection.put(PlayerIndex.PLAYER0, c);
            ClientConnection c1 = waitingConnection.get(0);
            //TODO: problem with RemoteView constructor
            /*RemoteView player1View = new RemoteView(PlayerIndex.PLAYER0, c1);
            player1View.addObserver(controller);
            controller.addRemoteView(PlayerIndex.PLAYER0, player1View);*/
            lobbyCount++;
        } else {
            waitingConnection.put(PlayerIndex.PLAYER1, c);
            waitingConnection.put(PlayerIndex.PLAYER2, c);
            if (controller.getPlayerNum() == 2 && waitingConnection.size() >= 2) {
                if(waitingConnection.size() == 3){
                    waitingConnection.remove(PlayerIndex.PLAYER2,c);
                }
                ClientConnection c2 = waitingConnection.get(1);
                RemoteView player2View = new RemoteView(PlayerIndex.PLAYER1, c2);
                player2View.addObserver(controller);
                controller.addRemoteView(PlayerIndex.PLAYER1, player2View);
                lobbyCount = 0;
            } else if (controller.getPlayerNum() == 3 && waitingConnection.size() == 3) {
                ClientConnection c2 = waitingConnection.get(1);
                ClientConnection c3 = waitingConnection.get(2);
                RemoteView player2View = new RemoteView(PlayerIndex.PLAYER1, c2);
                RemoteView player3View = new RemoteView(PlayerIndex.PLAYER2, c3);
                player2View.addObserver(controller);
                player3View.addObserver(controller);
                controller.addRemoteView(PlayerIndex.PLAYER1, player2View);
                controller.addRemoteView(PlayerIndex.PLAYER2, player3View);
                lobbyCount = 0;
            }
        }
    }

    public Server() throws IOException{
        this.serverSocket = new ServerSocket(PORT);
        System.out.println("Port is open ");
        pingThread = new Thread();
        pingThread.start();
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
            while(!pingThread.isInterrupted()){
                for(Map.Entry<PlayerIndex,ClientConnection> client : waitingConnection.entrySet()){
                    if(client != null && client.getValue().isConnected()){
                        client.getValue().ping(client.getKey());
                    }
                }
                try{
                    pingThread.sleep(1000);
                }catch (InterruptedException e){
                    System.err.println("Ping thread is interrupted");
                    e.printStackTrace();
                    pingThread.interrupt();
                }
            }
        }
    }


}