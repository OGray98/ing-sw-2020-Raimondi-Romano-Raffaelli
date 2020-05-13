package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class SelectWorkerMessage extends Message implements MessageToServer {

    private Position workerPos;

    public SelectWorkerMessage(PlayerIndex client, Position workerPos) {
        super(client, TypeMessage.SELECT_WORKER);
        if (workerPos == null) throw new NullPointerException("workerPos");
        this.workerPos = workerPos;
    }

    public Position getWorkerPos() {
        return workerPos;
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleSelectWorkerMessage(this);
    }
}
