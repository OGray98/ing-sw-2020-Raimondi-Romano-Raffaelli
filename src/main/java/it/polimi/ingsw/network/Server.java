package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Server {

    private static final int PORT = 12345;

    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final Map<PlayerIndex, ClientConnection> waitingConnection = new HashMap<>();


    private static int lobbyCount = 0;
    private Game game = new Game();
    private GameManager controller = new GameManager(game);
    private Thread pingThread;
    private Thread runThread;

    private boolean isActive = true;

    public synchronized void setActive(boolean condition){
        this.isActive = condition;
    }

    public synchronized boolean getActive(){
        return isActive;
    }

    private Server getServer(){
        return this;
    }

    /**
     * @param c connection to eliminate from the list of client player in lobby
     */
    public synchronized void deleteClient(ClientConnection c){
        this.waitingConnection.entrySet().stream().filter(entry -> entry.getValue().equals(c))
                .forEach(entry -> this.waitingConnection.remove(entry.getKey()));
        lobbyCount--;

    }

    /**
     * @param c connection to insert in lobby for the game
     */
    public synchronized void lobby(ClientConnection c) {

        if (lobbyCount == 0) {
            waitingConnection.put(PlayerIndex.PLAYER0, c);
            ClientConnection c1 = waitingConnection.get(PlayerIndex.PLAYER0);
            RemoteView player1View = new RemoteView(PlayerIndex.PLAYER0, c1);
            player1View.addObserver(controller);
            controller.addRemoteView(PlayerIndex.PLAYER0, player1View);
            lobbyCount++;
        }
        else if(lobbyCount == 1){
            waitingConnection.put(PlayerIndex.PLAYER1, c);
            if(waitingConnection.size() == 2){
                ClientConnection c2 = waitingConnection.get(PlayerIndex.PLAYER1);
                RemoteView player2View = new RemoteView(PlayerIndex.PLAYER1, c2);
                player2View.addObserver(controller);
                controller.addRemoteView(PlayerIndex.PLAYER1, player2View);
                lobbyCount++;
            }
        }
        else if (lobbyCount == 2 && controller.getPlayerNum() == 3) {
            waitingConnection.put(PlayerIndex.PLAYER2,c);
            if(waitingConnection.size() == 3) {
                ClientConnection c3 = waitingConnection.get(PlayerIndex.PLAYER2);
                RemoteView player3View = new RemoteView(PlayerIndex.PLAYER2, c3);
                player3View.addObserver(controller);
                controller.addRemoteView(PlayerIndex.PLAYER2, player3View);
                lobbyCount++;
                }
            }
        }

     public Thread pingRunThread(){
        Thread t = new Thread(() -> {
            while(getActive()) {
                waitingConnection.forEach(
                        (key, value) -> {
                            try {
                                value.ping(key);
                            } catch (IOException e) {
                                System.out.println("Tolgo connessione con " + key);
                                value.closeConnection();
                            }
                        }
                );

                final List<PlayerIndex> connectionListInactive = waitingConnection.entrySet().stream()
                        .filter(entry -> !entry.getValue().isConnected())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
                connectionListInactive.forEach(waitingConnection::remove);


                try {
                    pingThread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Ping thread is interrupted");
                    Logger.getAnonymousLogger().severe(e.getMessage());
                    setActive(false);
                    pingThread.interrupt();
                }
            }
        });
        t.start();
        return t;

     }

     public Thread threadInConnection(){
        Thread t = new Thread(() -> {
            while (true){
                try{
                    Socket socket = serverSocket.accept();
                    System.out.println("Client is connected");
                    SocketClientConnection socketClientConnection = new SocketClientConnection(socket,getServer());
                    executor.submit(socketClientConnection);
                }catch (IOException e){
                    System.err.println("Error during the open port on server");
                    Logger.getAnonymousLogger().severe(e.getMessage());
                    runThread.interrupt();
                    break;
                }
            }
            executor.shutdown();
        });
        t.start();
        return t;
     }


    public Server() throws IOException{
        this.serverSocket = new ServerSocket(PORT);
        System.out.println("Port is open ");
    }

    public void run(){
            try {
               pingThread = pingRunThread();
               runThread = threadInConnection();
               runThread.join();
               pingThread.join();
            }catch (InterruptedException | NoSuchElementException e){
                System.err.println("Error during the join of threads");
                Logger.getAnonymousLogger().severe(e.getMessage());
                runThread.interrupt();
                pingThread.interrupt();

            }
        }
    }


