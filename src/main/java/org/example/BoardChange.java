package org.example;

import java.util.HashMap;
import java.util.Map;

public class BoardChange {
    private Map<PositionContainer, PlayerIndex> playerChanges;
    private Position positionBuild;
    private BuildType buildType;
    private boolean cantGoUp;

    BoardChange(boolean canGoUp) {
        this.cantGoUp = canGoUp;
        this.playerChanges = null;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;
    }

    BoardChange() {
        this.cantGoUp = false;
        this.playerChanges = null;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;
    }

    public boolean getCanGoUp() {
        return cantGoUp;
    }

    public Map<PositionContainer, PlayerIndex> getChanges() throws NullPointerException {
        if (playerChanges == null)
            throw new NullPointerException("changes");
        return playerChanges;
    }

    public Position getPositionBuild() {
        return positionBuild;
    }

    public BuildType getBuildType() {
        return buildType;
    }

    public void addPlayerChanges(Position oldPosition, Position newPosition, PlayerIndex playerIndex) {
        PositionContainer posCont = new PositionContainer(oldPosition);
        posCont.put(newPosition);
        if (playerChanges == null)
            playerChanges = new HashMap<>();
        playerChanges.put(posCont, playerIndex);
    }

    public void setBuildPosition(Position buildPosition, BuildType type) {
        this.positionBuild = buildPosition;
        this.buildType = type;
    }

    public void setCantGoUp(boolean cantGoUp) {
        this.cantGoUp = cantGoUp;
    }

}
