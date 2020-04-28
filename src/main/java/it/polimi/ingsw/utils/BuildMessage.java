package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class BuildMessage extends Message {

    private final Position buildPos;

    public BuildMessage(PlayerIndex client, Position buildPos) {
        super(client, TypeMessage.BUILD);
        if (buildPos == null) throw new NullPointerException("buildPos");
        this.buildPos = buildPos;
    }

    public Position getBuildPosition() {
        return buildPos;
    }
}
