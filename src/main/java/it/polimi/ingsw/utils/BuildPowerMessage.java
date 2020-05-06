package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class BuildPowerMessage extends Message {

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
    public BuildType getBuildType(){
        return buildType;
    }
}
