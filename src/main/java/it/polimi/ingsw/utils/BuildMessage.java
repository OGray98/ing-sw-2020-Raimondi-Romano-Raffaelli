package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MessageControllable;
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

    @Override
    public void execute(MessageControllable controllable) throws NullPointerException {
        super.execute(controllable);
        controllable.handleBuildMessage(this);
    }
}
