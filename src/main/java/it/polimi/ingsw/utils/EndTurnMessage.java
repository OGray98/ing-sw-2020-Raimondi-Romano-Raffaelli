package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class EndTurnMessage extends Message{

    public EndTurnMessage(PlayerIndex client){
        super(client, TypeMessage.END_TURN);
    }
}
