package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MessageControllable;
import it.polimi.ingsw.model.player.PlayerIndex;

public class EndTurnMessage extends Message {

    public EndTurnMessage(PlayerIndex client) {
        super(client, TypeMessage.END_TURN);
    }

    @Override
    public void execute(MessageControllable controllable) throws NullPointerException {
        super.execute(controllable);
        controllable.handleEndTurnMessage(this);
    }
}
