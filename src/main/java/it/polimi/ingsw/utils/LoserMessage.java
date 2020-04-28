package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class LoserMessage extends Message {

    private final PlayerIndex loserPlayer;

    public LoserMessage(PlayerIndex loserPlayer) {
        super(PlayerIndex.ALL, TypeMessage.LOSER);
        this.loserPlayer = loserPlayer;
    }

    public PlayerIndex getLoserPlayer() {
        return loserPlayer;
    }
}
