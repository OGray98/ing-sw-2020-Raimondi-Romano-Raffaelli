package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message used to notify the view from ClientModel after a build action
 * It contains the level of the new building
 * */
public class BuildViewMessage extends Message{
    private final Position buildPos;
    private int level;

    public BuildViewMessage(PlayerIndex client, Position buildPos, int level) {
        super(client, TypeMessage.BUILD);
        if (buildPos == null) throw new NullPointerException("buildPos");
        this.buildPos = buildPos;
        if(level > 4 || level < 1) throw new IllegalArgumentException("not valid level");
        this.level = level;
    }

    public Position getBuildPosition() {
        return buildPos;
    }
    public int getLevel(){
        return this.level;
    }
}
