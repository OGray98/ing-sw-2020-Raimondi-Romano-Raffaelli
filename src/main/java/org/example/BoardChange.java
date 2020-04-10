package org.example;

import java.util.HashMap;
import java.util.Map;

public class BoardChange {
    private Map<PositionContainer, PlayerIndex> playerChanges;
    private Position positionBuild;
    private BuildType buildType;
    private boolean cantGoUp;

    /*
     * Create a BoardChange with the passed cantGoUp and every other field null
     */
    BoardChange(boolean cantGoUp) {
        this.cantGoUp = cantGoUp;
        this.playerChanges = null;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;
    }

    /*
     * Create a BoardChange with every field null
     */
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

    public Position getPositionBuild() throws NullPointerException {
        if (positionBuild == null)
            throw new NullPointerException("positionBuild");
        return positionBuild;
    }

    public BuildType getBuildType() {
        return buildType;
    }

    /*
     * If playerChange isn't initialized, this method create it. Then add an entry in playerChange with a PositionContainer
     * which contains oldPosition and newPosition to represent the move and the relative PlayerIndex
     * It requires a Position which is the oldPosition of movement
     * It requires a Position which is the newPosition of movement
     * It requires a PlayerIndex which is the player of movement
     */
    public void addPlayerChanges(Position oldPosition, Position newPosition, PlayerIndex playerIndex) throws NullPointerException {
        if (oldPosition == null)
            throw new NullPointerException("oldPosition");
        if (newPosition == null)
            throw new NullPointerException("newPosition");

        PositionContainer posCont = new PositionContainer(oldPosition);
        posCont.put(newPosition);
        if (playerChanges == null)
            playerChanges = new HashMap<>();
        playerChanges.put(posCont, playerIndex);
    }
    
    public void setBuildPosition(Position buildPosition, BuildType type) throws NullPointerException {
        if (positionBuild == null)
            throw new NullPointerException("positionBuild");
        this.positionBuild = buildPosition;
        this.buildType = type;
    }

    public void setCantGoUp(boolean cantGoUp) {
        this.cantGoUp = cantGoUp;
    }

}
