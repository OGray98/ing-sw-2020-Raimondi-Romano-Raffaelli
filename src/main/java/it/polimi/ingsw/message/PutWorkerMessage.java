package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * PutWorkerMessage extends Message and it contains two positions where the player
 * wants to put his workers at the start of the game
 */
public class PutWorkerMessage extends TwoPositionMessage implements MessageToServer, MessageToClient, MessageToView {


    public PutWorkerMessage(PlayerIndex client, Position pos1, Position pos2) {
        super(client, TypeMessage.PUT_WORKER, pos1, pos2);
    }

    public Position getPositionOne() {
        return super.getPositions().get(0);
    }

    public Position getPositionTwo() {
        return super.getPositions().get(1);
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handlePutWorkerMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updatePutWorkerMessage(this);
    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updatePutWorker(this);
    }
}
