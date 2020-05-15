package it.polimi.ingsw.Client;

import it.polimi.ingsw.utils.ActionMessage;
import it.polimi.ingsw.utils.BuildViewMessage;
import it.polimi.ingsw.utils.MoveMessage;
import it.polimi.ingsw.utils.PutWorkerMessage;

public interface ControllableByViewMessage {

    /**
     * Method that update the view when player put a worker
     * Implented in GUI or CLI
     * */
    public void updatePutWorker(PutWorkerMessage message);

    public void updateMoveWorker(MoveMessage message);

    public void updateBuild(BuildViewMessage message);

    public void updateActionView(ActionMessage message);
}
