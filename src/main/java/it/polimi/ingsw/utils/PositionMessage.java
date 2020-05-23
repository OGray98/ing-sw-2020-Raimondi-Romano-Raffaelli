package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class PositionMessage extends Message implements MessageToServer {

    private final Position position;

    public PositionMessage(PlayerIndex client, Position position) {
        super(client, TypeMessage.POSITION);
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {

    }
}
