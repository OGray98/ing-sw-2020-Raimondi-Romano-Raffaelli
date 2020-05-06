package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class CurrentPlayerMessage extends Message{

    private PlayerIndex currentPlayerIndex;

    public CurrentPlayerMessage(PlayerIndex currentPlayerIndex) {
        super(PlayerIndex.ALL, TypeMessage.CURRENT_PLAYER);
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public PlayerIndex getCurrentPlayerIndex(){
        return this.currentPlayerIndex;
    }
}
