package it.polimi.ingsw.view;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.Message;

/**
 * RemoteView is an abstract class which represents an abstraction of RemoteView
 * in the pattern MVC.
 */
public class RemoteView extends View implements Observer<Board> {

    private static Board board;

    public RemoteView(PlayerIndex player, Observable<Message> observable) {
        super(player);
        if (board == null)
            board = new Board();
        observable.addObserver(new MessageReceiver());
    }

    /**
     * MessageReceiver is a class used by RemoteView to Observer the
     * messages sent by clients.
     */
    private class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            handleMessage(message);
        }
    }

    public static Board getBoard() throws NullPointerException {
        if (board == null)
            throw new NullPointerException("board");
        return board;
    }


    @Override
    public void update(Board message) {
        RemoteView.board = message;
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
