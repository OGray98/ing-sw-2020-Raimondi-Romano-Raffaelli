package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.player.PlayerIndex;

public class UpdateStateMessage extends Message implements MessageToClient {

    private final GameState gameState;

    public UpdateStateMessage(PlayerIndex client, GameState gameState) {
        super(client, TypeMessage.UPDATE_STATE);
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateState(this);
    }
}
