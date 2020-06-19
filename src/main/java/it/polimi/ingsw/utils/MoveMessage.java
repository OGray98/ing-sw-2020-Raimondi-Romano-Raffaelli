package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message sent when player move on other cell during Move-State
 */
public class MoveMessage extends TwoPositionMessage implements MessageToServer, MessageToClient, MessageToView {

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
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleMoveMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateMoveMessage(this);
    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateMoveWorker(this);
    }
}
