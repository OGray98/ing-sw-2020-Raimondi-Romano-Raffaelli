package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class ConnectionPlayerIndex extends Message implements MessageToClient {

    private PlayerIndex playerIndex;

    public ConnectionPlayerIndex(PlayerIndex client) {
        super(client, TypeMessage.PLAYERINDEX_CONNECTION);
        this.playerIndex = client;
    }

    public PlayerIndex getPlayerIndex() {
        return this.playerIndex;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateIndex(this);
    }
}
