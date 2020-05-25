package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class PositionMessage extends Message implements MessageToServer, MessageToView {

    private final Position position;
    private final Boolean isUsingPower;

    public PositionMessage(PlayerIndex client, Position position, boolean isUsingPower) {
        super(client, TypeMessage.POSITION);
        this.position = position;
        this.isUsingPower = isUsingPower;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {

    }

    public boolean isUsingPower() {
        return isUsingPower;
    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateActions(this);
    }
}
