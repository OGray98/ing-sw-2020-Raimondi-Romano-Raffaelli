package it.polimi.ingsw.network;

import it.polimi.ingsw.message.MessageToClient;
import it.polimi.ingsw.message.MessageToServer;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;

/**
 * Interface used by client and socketClientConnection to connect the client connection with server connection and
 * permit them to communicate
 */
public interface ClientConnection {

    /**
     * Close the socket, input-stream and output-stream of connection
     */
    void closeConnection();

    /**
     * @param observer
     * add observer
     */
    void addObserver(Observer<MessageToServer> observer);

    /**
     * @param message
     * send a response message to client from server
     */
    void asyncSend(MessageToClient message);

    /**
     * @param clientIndex
     * set client index
     */
    void setClientIndex(PlayerIndex clientIndex);

    /**
     * @return boolean true if server is connected to client
     */
    boolean isConnected();

    /**
     * @param player of relative client
     * @throws IOException error on input or output stream
     *thread that send a ping message to client to verify if he is alive
     */
    void ping(PlayerIndex player) throws IOException;

    /**
     * set the connection to close
     */
    void forceDisconnection();
}