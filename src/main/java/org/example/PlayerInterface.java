package org.example;

import java.util.List;
import java.util.Map;

public interface PlayerInterface {

    /* Set the situation when worker has never been moved yet */
    void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp);

    /* Each time a user select a worker tile, Game will set the needed information through this method */
    void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp);

    /* Set the situation after a move */
    void setAfterMove(Cell oldCell, Cell cellOccupied);

    /*When a Player can use a God power activePower will be set true, false otherwise*/
    void setActivePower(boolean isPowerOn);

    /*Method that returns true if user select a possible move action
    * It requires a List<Cell> that contains all the cells adjacent to the worker selected
    * It requires a Map<Position, PlayerIndex> that contains all the players adjacent to the selected worker
    * It requires a Position that is the position to check
    * Throws InvalidPositionException if movePos is an illegal position
    * Throws NullPointerException is adjacentCells or adjacentPlayerList is null*/
    boolean canMove(List<Cell> adjacentCells, Map<Position,PlayerIndex> adjacentPlayerList, Position movePos)throws InvalidPositionException, NullPointerException;

    /*Method that returns true if user select a possible build action
     * It requires a List<Cell> that contains all the cells adjacent to the worker selected
     * It requires a Map<Position, PlayerIndex> that contains all the players adjacent to the selected worker
     * It requires a Position that is the position to check
     * Throws InvalidPositionException if movePos is an illegal position
     * Throws NullPointerException is adjacentCells or adjacentPlayerList is null*/
    boolean canBuild(List<Cell> adjacentList, Map<Position,PlayerIndex> adjacentPlayerList, Position buildPos) throws InvalidPositionException, NullPointerException;

    /* Method that returns true if is verified a win condition
    * Throws NullPointerException if is not selected any worker */
    boolean hasWin() throws NullPointerException;

    /* Update the occupiedCell after a move, so the hasWin() method can runs correctly
    * in Decorator class can set activePower */
    void move(Cell newOccupiedCell) throws NullPointerException;

    /*In Decorator classes can set active power, called after a build action*/
    void activePowerAfterBuild();

    /*Method that will be specialized in the Decorator class, it refers to a specific God power*/
    boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition);

    /*Method that implements the power of a specific God, specialized in Decorator*/
    BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition);

    Cell getOldCell() throws NullPointerException;

    Cell getCellOccupied() throws NullPointerException;

    //public God getGodName();

    /*boolean getCantGoUp();*/

    boolean getActivePower();
}
