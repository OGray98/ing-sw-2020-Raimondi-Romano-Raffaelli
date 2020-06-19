package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message sent from player when he want to use a power (not power build)
 */
public class UsePowerMessage extends TwoPositionMessage implements MessageToServer {

    public UsePowerMessage(PlayerIndex client, Position workerPos, Position powerPos) {
        super(client, TypeMessage.USE_POWER, workerPos, powerPos);
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (powerPos == null)
            throw new NullPointerException("powerPos");
    }

    public Position getWorkerPosition() {
        return super.getPositions().get(0);
    }

    public Position getPowerPosition() {
        return super.getPositions().get(1);
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleUsePowerMessage(this);
    }
}
