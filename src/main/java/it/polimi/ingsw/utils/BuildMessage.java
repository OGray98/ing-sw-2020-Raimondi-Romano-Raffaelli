package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class BuildMessage extends Message implements MessageToServer, MessageToClient {

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
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleBuildMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateBuildMessage(this);
    }
}
