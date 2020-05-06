package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class ConnectionPlayerIndex extends Message{

    private PlayerIndex playerIndex;

    public ConnectionPlayerIndex(PlayerIndex client){
        super(client, TypeMessage.PLAYERINDEX_CONNECTION);
        this.playerIndex = client;
    }

    public PlayerIndex getPlayerIndex(){
        return this.playerIndex;
    }
}
