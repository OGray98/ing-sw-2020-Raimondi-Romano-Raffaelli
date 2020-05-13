package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * GodLikeChooseFirstPlayerMessage extends Message and it contains the PlayerIndex of the First Player
 * chosen by the GodLike Player
 */

public class GodLikeChooseFirstPlayerMessage extends Message implements MessageToServer {

    private final PlayerIndex playerFirst;

    public GodLikeChooseFirstPlayerMessage(PlayerIndex client, PlayerIndex playerFirst) {
        super(client, TypeMessage.GODLIKE_CHOOSE_FIRST_PLAYER);
        this.playerFirst = playerFirst;
    }

    public PlayerIndex getPlayerFirst() {
        return this.playerFirst;
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleGodLikeChooseFirstPlayerMessage(this);
    }
}
