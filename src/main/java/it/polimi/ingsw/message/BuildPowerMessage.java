package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message used to notify a building action by a god power
 * It contains the position of the build
 * */
public class BuildPowerMessage extends Message implements MessageToClient {

    private final Position buildPos;
    private final BuildType buildType;

    public BuildPowerMessage(PlayerIndex client, Position buildPos, BuildType buildType) {
        super(client, TypeMessage.BUILD_POWER);
        if (buildPos == null) throw new NullPointerException("buildPos");
        this.buildPos = buildPos;
        this.buildType = buildType;
    }

    public Position getBuildPosition() {
        return buildPos;
    }

    public BuildType getBuildType() {
        return buildType;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateBuildPowerMessage(this);
    }
}
