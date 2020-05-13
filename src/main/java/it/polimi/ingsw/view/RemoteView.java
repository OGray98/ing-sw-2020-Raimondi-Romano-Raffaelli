package it.polimi.ingsw.view;

import it.polimi.ingsw.exception.WrongAssociationViewPlayerException;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.ClientConnection;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;
import it.polimi.ingsw.utils.MessageToServer;

/**
 * RemoteView is an abstract class which represents an abstraction of RemoteView
 * in the pattern MVC.
 */
public class RemoteView extends View implements Observer<Message> {

    private ClientConnection clientConnection;

    public RemoteView(PlayerIndex player, ClientConnection connection) {
        super(player);
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
    public void update(Message message) throws NullPointerException {
        if (message == null)
            throw new NullPointerException("msg");
        sendUpdates(message);
    }

    public void putMessage(Message msg) throws NullPointerException, WrongAssociationViewPlayerException {
        if (msg == null)
            throw new NullPointerException("msg");
        if (!(msg.getClient().equals(getPlayer())))
            throw new WrongAssociationViewPlayerException(getPlayer(), msg.getClient());
        sendUpdates(msg);
    }

    public void sendUpdates(Message message) {
        clientConnection.asyncSend(message);
    }

    /*
    @Override
    public void update(BoardChange message) {
        if (!message.isPlayerChangesNull()) {
            message.getChanges().entrySet().stream()
                    .filter(entry -> board.getWorkerNum(entry.getValue()) == 2)
                    .forEach(
                            workerToMove -> board.changeWorkerPosition(
                                    workerToMove.getKey().getOldPosition(),
                                    workerToMove.getKey().getOccupiedPosition()
                            )
                    );
            message.getChanges().entrySet().stream()
                    .filter(entry -> board.getWorkerNum(entry.getValue()) < 2)
                    .forEach(workerToPut -> board.putWorker(workerToPut.getKey().getOccupiedPosition(), workerToPut.getValue()));
        }
        if (!message.isPositionBuildNull()) {
            if (message.getBuildType() == BuildType.DOME) {
                Cell aus = board.getCell(message.getPositionBuild());
                aus.setHasDome(true);
                board.setCell(aus);
            } else
                board.constructBlock(message.getPositionBuild());
        }
    }*/
}