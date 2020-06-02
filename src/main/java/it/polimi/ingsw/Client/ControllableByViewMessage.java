package it.polimi.ingsw.Client;

import it.polimi.ingsw.utils.*;

public interface ControllableByViewMessage {

    /**
     * Method that update the view when player put a worker
     * Implented in GUI or CLI
     * */
    public void updatePutWorker(PutWorkerMessage message);

    public void updateMoveWorker(MoveMessage message);

    public void updateBuild(BuildViewMessage message);

    public void updateSelectedCardView(PlayerSelectGodMessage message);

    public void updateActions(PositionMessage message);

    void updateClientIndex(ConnectionPlayerIndex message);

    /**
     * Method that remove a loser player from the board
     * */
    void updateRemovePlayer(RemovePlayerMessage message);
}
