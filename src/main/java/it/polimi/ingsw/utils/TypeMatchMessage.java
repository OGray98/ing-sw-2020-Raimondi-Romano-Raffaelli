package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * TypeMatchMessage extends Message and represent an exchanged Message that say if
 * this is a two or three players game
 */
public class TypeMatchMessage extends Message {

    private final boolean isThreePlayersMatch;

    public TypeMatchMessage(PlayerIndex client, boolean isThreePlayersMatch) {
        super(client, TypeMessage.IS_THREE_PLAYERS_GAME);
        this.isThreePlayersMatch = isThreePlayersMatch;
    }

    public boolean isThreePlayersMatch() {
        return isThreePlayersMatch;
    }
}
