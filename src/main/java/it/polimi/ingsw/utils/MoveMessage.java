package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MessageControllable;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class MoveMessage extends TwoPositionMessage {

    public MoveMessage(PlayerIndex client, Position workerPos, Position movePos) throws NullPointerException {
        super(client, TypeMessage.MOVE, workerPos, movePos);
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (movePos == null)
            throw new NullPointerException("movePos");
    }

    public Position getWorkerPosition() {
        return super.getPositions().get(0);
    }

    public Position getMovePosition() {
        return super.getPositions().get(1);
    }

    @Override
    public void execute(MessageControllable controllable) throws NullPointerException {
        super.execute(controllable);
        controllable.handleMoveMessage(this);
    }
}
