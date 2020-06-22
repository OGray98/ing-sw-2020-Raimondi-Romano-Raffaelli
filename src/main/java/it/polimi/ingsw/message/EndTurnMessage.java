package it.polimi.ingsw.message;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class EndTurnMessage extends Message implements MessageToServer {

    public EndTurnMessage(PlayerIndex client) {
        super(client, TypeMessage.END_TURN);
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleEndTurnMessage(this);
    }
}
