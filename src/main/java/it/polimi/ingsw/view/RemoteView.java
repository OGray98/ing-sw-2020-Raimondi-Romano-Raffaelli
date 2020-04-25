package it.polimi.ingsw.view;

import it.polimi.ingsw.ClientConnection;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observer;

public class RemoteView implements Observer<Board> {

    private class MessageReceiver implements Observer<String>{

        @Override
        public void update(String message) {
            System.out.println("Received: " + message);
        }
    }

    private ClientConnection clientConnection;
    private Player player;
    private static Board board;

    public RemoteView(Player player, String opponent, ClientConnection c){
        this.player = player;
        this.clientConnection = c;
        c.addObserver(new MessageReceiver());
        c.asyncSend("Your opponent is: " + opponent);
    }

    public RemoteView() {
        if (board == null)
            board = new Board();
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
