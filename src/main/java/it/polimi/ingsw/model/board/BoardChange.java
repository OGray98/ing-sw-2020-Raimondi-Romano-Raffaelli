package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exception.SameMovementException;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.HashMap;
import java.util.Map;

public class BoardChange {
    private Map<PositionContainer, PlayerIndex> playerChanges;
    private final Position positionBuild;
    private final BuildType buildType;
    private final Boolean cantGoUp;
    private final boolean isPlayerChangesNull;
    private final boolean isPositionBuildNull;
    private final boolean isCantGoUpNull;

    /*
     * Create a BoardChange with the passed cantGoUp and every other field null
     */
    public BoardChange(boolean cantGoUp) {
        this.cantGoUp = cantGoUp;
        this.playerChanges = null;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;

        isPlayerChangesNull = true;
        isPositionBuildNull = true;
        isCantGoUpNull = false;
    }

    public BoardChange(Position oldPosition, Position newPosition, PlayerIndex playerIndex, boolean cantGoUp) throws NullPointerException {
        if (oldPosition == null)
            throw new NullPointerException("oldPosition");
        if (newPosition == null)
            throw new NullPointerException("newPosition");

        PositionContainer posCont = new PositionContainer(oldPosition);
        posCont.put(newPosition);
        playerChanges = new HashMap<>();
        playerChanges.put(posCont, playerIndex);

        this.cantGoUp = cantGoUp;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;

        isPlayerChangesNull = false;
        isPositionBuildNull = true;
        isCantGoUpNull = true;
    }

    public BoardChange(Position oldPosition, Position newPosition, PlayerIndex playerIndex) throws NullPointerException {
        if (oldPosition == null)
            throw new NullPointerException("oldPosition");
        if (newPosition == null)
            throw new NullPointerException("newPosition");

        PositionContainer posCont = new PositionContainer(oldPosition);
        posCont.put(newPosition);
        playerChanges = new HashMap<>();
        playerChanges.put(posCont, playerIndex);

        this.cantGoUp = null;
        this.positionBuild = null;
        this.buildType = BuildType.LEVEL;

        isPlayerChangesNull = false;
        isPositionBuildNull = true;
        isCantGoUpNull = true;
    }

    public BoardChange(Position buildPosition, BuildType type) throws NullPointerException {
        if (buildPosition == null)
            throw new NullPointerException("buildPosition");
        this.positionBuild = buildPosition;
        this.buildType = type;

        this.playerChanges = null;
        this.cantGoUp = null;

        isPlayerChangesNull = true;
        isPositionBuildNull = false;
        isCantGoUpNull = true;
    }

    public boolean isPlayerChangesNull() {
        return isPlayerChangesNull;
    }

    public boolean isPositionBuildNull() {
        return isPositionBuildNull;
    }

    public boolean isCantGoUpNull() {
        return isCantGoUpNull;
    }

    public boolean getCantGoUp() throws NullPointerException {
        if (cantGoUp == null)
            throw new NullPointerException("cantGoUp");
        return cantGoUp;
    }

    public Map<PositionContainer, PlayerIndex> getChanges() throws NullPointerException {
        if (playerChanges == null)
            throw new NullPointerException("playerChanges");
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
        else {
            for (Map.Entry<PositionContainer, PlayerIndex> entry : playerChanges.entrySet()) {
                if (entry.getKey().equals(posCont)) {
                    throw new SameMovementException(posCont.toString());
                }
            }
        }

        playerChanges.put(posCont, playerIndex);
    }

}
