package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public interface PlayerInterface {

    /* Set the situation when worker has never been moved yet */
    void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp);

    /* Each time a user select a worker tile, Game will set the needed information through this method */
    void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp);

    /* Set the situation after a move */
    void setAfterMove(Cell oldCell, Cell cellOccupied);

    /*Setter of cantGoUp*/
    void setCantGoUp(boolean cantGoUp);

    /*Method that returns true if user select a possible move action
     * It requires a List<Cell> that contains all the cells adjacent to the worker selected
     * It requires a Map<Position, PlayerIndex> that contains all the players adjacent to the selected worker
     * It requires a Position that is the position to check
     * Throws InvalidPositionException if movePos is an illegal position
     * Throws NullPointerException is adjacentCells or adjacentPlayerList is null*/
    boolean canMove(Map<Position, PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException, NullPointerException;

    boolean canMove(Map<Position, PlayerIndex> adjacentPlayerList, Cell moveCell, Cell occupiedCell, boolean cantGoUp) throws InvalidPositionException, NullPointerException;

    /*Method that returns true if user select a possible build action
     * It requires a List<Cell> that contains all the cells adjacent to the worker selected
     * It requires a Map<Position, PlayerIndex> that contains all the players adjacent to the selected worker
     * It requires a Position that is the position to check
     * Throws InvalidPositionException if movePos is an illegal position
     * Throws NullPointerException is adjacentCells or adjacentPlayerList is null*/
    boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException;

    /* Method that returns true if is verified a win condition
    * Throws NullPointerException if is not selected any worker */
    boolean hasWin() throws NullPointerException;

    /* Update the occupiedCell after a move, so the hasWin() method can runs correctly
    * in Decorator class can set activePower */
    void move(Cell newOccupiedCell) throws NullPointerException;

    /*Method that will be specialized in the Decorator class, it refers to a specific God power*/
    boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList);

    /*Method that implements the power of a specific God, specialized in Decorator*/
    BoardChange usePower(Cell powerCell);

    Cell getOldCell() throws NullPointerException;

    Cell getCellOccupied() throws NullPointerException;

    boolean getCantGoUp();

    PlayerIndex getPlayerNum();

    int getPowerListDimension();

    String getGodName();

    GameState getPowerState();

    GameState getNextState();


    //public God getGodName();
}
