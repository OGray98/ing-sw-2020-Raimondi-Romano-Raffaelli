package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message sent when a player lose the game
 */
public class LoserMessage extends Message implements MessageToClient{

    private final PlayerIndex loserPlayer;

    public LoserMessage(PlayerIndex client, PlayerIndex loserPlayer) {
        super(client, TypeMessage.LOSER);
        this.loserPlayer = loserPlayer;
    }

    public PlayerIndex getLoserPlayer() {
        return loserPlayer;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateLoserMessage(this);
    }
}
