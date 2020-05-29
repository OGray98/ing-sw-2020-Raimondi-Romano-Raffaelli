package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.WrongAssociationViewPlayerException;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.MessageToClient;
import it.polimi.ingsw.utils.MessageToServer;

/**
 * RemoteView is an abstract class which represents an abstraction of RemoteView
 * in the pattern MVC.
 */
public class RemoteView extends View implements Observer<MessageToClient> {

    private final ClientConnection clientConnection;

    public RemoteView(PlayerIndex player, ClientConnection connection) {
        super.setPlayer(player);
        this.clientConnection = connection;
        connection.addObserver(new MessageReceiver());
    }

    /**
     * MessageReceiver is a class used by RemoteView to Observer the
     * messages sent by clients.
     */
    private class MessageReceiver implements Observer<MessageToServer> {

        @Override
        public void update(MessageToServer message) {
            handleMessage(message);
        }
    }

    @Override
    public void update(MessageToClient message) throws NullPointerException {
        if (message == null)
            throw new NullPointerException("msg");
        sendUpdates(message);
    }

    public void putMessage(MessageToClient msg) throws NullPointerException, WrongAssociationViewPlayerException {
        if (msg == null)
            throw new NullPointerException("msg");
        /*if (!(msg.getClient().equals(getPlayer())))
            throw new WrongAssociationViewPlayerException(getPlayer(), msg.getClient());*/
        sendUpdates(msg);
    }

    public void sendUpdates(MessageToClient message) {
        clientConnection.asyncSend(message);
    }
}