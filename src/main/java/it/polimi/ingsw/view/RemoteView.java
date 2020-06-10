package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.WrongAssociationViewPlayerException;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.CloseConnectionMessage;
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

    public void disconnect() {
        putMessage(new CloseConnectionMessage(super.getPlayer()));
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
        System.out.println("Send message " + message.getType() + " to " + message.getClient());
    }

    public void putMessage(MessageToClient msg) throws NullPointerException, WrongAssociationViewPlayerException {
        if (msg == null)
            throw new NullPointerException("msg");
        /*if (!(msg.getClient().equals(getPlayer())))
            throw new WrongAssociationViewPlayerException(getPlayer(), msg.getClient());*/
        sendUpdates(msg);
        System.out.println("Send message " + msg.getType() + " to " + msg.getClient());
    }

    public void sendUpdates(MessageToClient message) {
        clientConnection.asyncSend(message);
    }
}