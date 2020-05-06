package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class EndGameMessage extends Message {

    public EndGameMessage(PlayerIndex client) {
        super(client, TypeMessage.END_GAME);
    }

    @Override
    public String toString() {
        return "Game is ended!";
    }
}
