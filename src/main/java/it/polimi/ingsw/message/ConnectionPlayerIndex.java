package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class ConnectionPlayerIndex extends Message implements MessageToClient, MessageToView {

    private final PlayerIndex playerIndex;

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

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateClientIndex(this);
    }
}