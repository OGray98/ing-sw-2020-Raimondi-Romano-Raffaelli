package org.example;

import java.util.List;
import java.util.Map;

public abstract class PlayerDecorator implements PlayerInterface {
    private PlayerInterface player;
    private final String godName;
    private final String godDescription;

    public PlayerDecorator(PlayerInterface player, String godName, String godDescription) {
        this.player = player;
        this.godName = godName;
        this.godDescription = godDescription;
    }

    public String getGodDescription() {
        return godDescription;
    }

    public String getGodName() {
        return godName;
    }

    /* Set the situation when worker has never been moved yet */
    public void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp) {
        if (cellOccupied == null)
            throw new NullPointerException("cellOccupied");
        if (player == null)
            throw new NullPointerException("player");
        player.setStartingWorkerSituation(cellOccupied, cantGoUp);
    }

    /* Each time a user select a worker tile, Game will set the needed information through this method */
    public void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp) {
        if (cellOccupied == null)
            throw new NullPointerException("cellOccupied");
        if (player == null)
            throw new NullPointerException("player");
        if (oldCell == null)
            throw new NullPointerException("oldCell");
        player.setWorkerSituation(oldCell, cellOccupied, cantGoUp);
    }

    /* Set the situation after a move */
    public void setAfterMove(Cell oldCell, Cell cellOccupied) {
        if (cellOccupied == null)
            throw new NullPointerException("cellOccupied");
        if (player == null)
            throw new NullPointerException("player");
        if (oldCell == null)
            throw new NullPointerException("oldCell");
        player.setAfterMove(oldCell, cellOccupied);
    }

    public void setActivePower(boolean isPowerOn) {
        if (player == null)
            throw new NullPointerException("player");
        player.setActivePower(isPowerOn);
    }

    /*Method that returns true if user select a possible move action
     * It requires a List<Cell> that contains all the cells adjacent to the worker selected
     * It requires a Map<Position, PlayerIndex> that contains all the players adjacent to the selected worker
     * It requires a Position that is the position to check
     * Throws InvalidPositionException if movePos is an illegal position
     * Throws NullPointerException is adjacentCells or adjacentPlayerList is null*/
    public boolean canMove(List<Cell> adjacentCells, Map<Position, PlayerIndex> adjacentPlayerList, Position movePos) throws InvalidPositionException, NullPointerException {
        if (player == null)
            throw new NullPointerException("player");
        return player.canMove(adjacentCells, adjacentPlayerList, movePos);
    }

    /*Method that returns true if user select a possible build action
     * It requires a List<Cell> that contains all the cells adjacent to the worker selected
     * It requires a Map<Position, PlayerIndex> that contains all the players adjacent to the selected worker
     * It requires a Position that is the position to check
     * Throws InvalidPositionException if movePos is an illegal position
     * Throws NullPointerException is adjacentCells or adjacentPlayerList is null*/
    public boolean canBuild(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position buildPos) throws InvalidPositionException, NullPointerException {
        if (player == null)
            throw new NullPointerException("player");
        return player.canBuild(adjacentList, adjacentPlayerList, buildPos);
    }

    /* Method that returns true if is verified a win condition */
    public boolean hasWin() throws NullPointerException {
        if (player == null)
            throw new NullPointerException("player");
        return player.hasWin();
    }

    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        return false;
    }
}
