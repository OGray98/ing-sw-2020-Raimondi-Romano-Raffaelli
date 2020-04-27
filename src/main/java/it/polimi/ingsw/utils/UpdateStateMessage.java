package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.player.PlayerIndex;

public class UpdateStateMessage extends Message {

    private final GameState gameState;

    public UpdateStateMessage(PlayerIndex client, GameState gameState) {
        super(client, TypeMessage.UPDATE_STATE);
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }
}
