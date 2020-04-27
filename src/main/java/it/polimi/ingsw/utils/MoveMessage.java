package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class MoveMessage extends TwoPositionMessage {

    public MoveMessage(PlayerIndex client, TypeMessage type, Position workerPos, Position movePos) throws NullPointerException {
        super(client, type, workerPos, movePos);
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
}
