package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class PositionMessage extends Message implements MessageToServer {

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
}
