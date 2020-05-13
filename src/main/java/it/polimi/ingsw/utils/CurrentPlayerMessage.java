package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class CurrentPlayerMessage extends Message implements MessageToClient {

    private PlayerIndex currentPlayerIndex;

    public CurrentPlayerMessage(PlayerIndex currentPlayerIndex) {
        super(currentPlayerIndex, TypeMessage.CURRENT_PLAYER);
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public PlayerIndex getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateCurrentPlayer(this);
    }
}
