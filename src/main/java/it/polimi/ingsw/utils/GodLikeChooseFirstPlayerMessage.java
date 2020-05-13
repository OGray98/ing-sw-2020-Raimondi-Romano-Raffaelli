package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MessageControllable;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * GodLikeChooseFirstPlayerMessage extends Message and it contains the PlayerIndex of the First Player
 * chosen by the GodLike Player
 * */

public class GodLikeChooseFirstPlayerMessage extends Message{

    private final PlayerIndex playerFirst;

    public GodLikeChooseFirstPlayerMessage(PlayerIndex client, PlayerIndex playerFirst) {
        super(client, TypeMessage.GODLIKE_CHOOSE_FIRST_PLAYER);
        this.playerFirst = playerFirst;
    }

    public PlayerIndex getPlayerFirst() {
        return this.playerFirst;
    }

    @Override
    public void execute(MessageControllable controllable) throws NullPointerException {
        super.execute(controllable);
        controllable.handleGodLikeChooseFirstPlayerMessage(this);
    }
}
