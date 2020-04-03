package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.WorkerNotPresentException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

import java.util.List;

public interface PlayerInterface {

    //Getter of the game board
    Board getBoard();

    //Getter of nickName
    String getNickName();

    //Get the godChosen of the Player
    God getGodChosen() throws NullPointerException;

    /* Get the worker number workerIndex
    * Throws InvalidIndexPlayerException if workerIndex < 0 || workerIndex > 1 */
    Worker getWorker(int workerIndex) throws InvalidIndexWorkerException;

    int getWorkerIndex(Position position) throws WorkerNotPresentException, InvalidPositionException;

    /* Get the Position of the Worker tile
    * Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1 */
    Position getWorkerPositionOccupied(int workerIndex) throws InvalidIndexPlayerException;

    void putWorker(Position startingCellPosition, int workerIndex) throws InvalidIndexWorkerException, InvalidPositionException;

    /* Move the Worker tile on the Position wanted
    * Throws InvalidIndexPlayerException if worker index is not a possible value */
    void moveWorker(Position newCellPosition, int workerIndex) throws InvalidIndexWorkerException;

    /* General check of possible moves, not specialized in particular Gods!
    * Controller must give the workerLevel (int)
    * If return value == true, Controller will procede with the calling of moveWorker() method
    * If return value == false, Controller must notify to the user to select an other Cell for the move phase
    * Throws InvalidPositionException if the Position moveToCheck is not a possible position
    * Throws NullPointerException if a null list is given
    * Throws IllegalArgumentException if an invalid workerLevel is given (0 <= workerLevel <= 3) */
    boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel) throws InvalidPositionException, NullPointerException, IllegalArgumentException;

    //Build on the given Position toBuildPosition
    void buildWorker(Position toBuildPosition);

    /* Build with the Worker tile in the Cell wanted
    * Throws NullPointerException if a null list is given
    * Throws InvalidPositionException if the Position buildingPosition is not a possible position */
    boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws InvalidPositionException, NullPointerException;

    /* Set godChosen of Player with a God at index godChosenNum in the chosenCards[] array of the Deck
    * Throws IllegalArgumentException if godChosenNum < 0 || godChosenNum > 2 */
    void chooseGodPower(God godChosen) throws IllegalArgumentException;

    //Verify from view if player want to use power
    boolean canUsePower(Position pos);

    //In a decorated PlayerDecorator, it implements the power related to the God chosen by the player
    void usePower(Position pos);

    /* Method that requires to be called after a moveWorker() ( not a putWorker()! ) and before a buildWorker() operation
    * It returns true if a Player moved the worker number workerIndex from a level 2 cell to a level 3 cell
    * It returns false in all other cases
    * Throws NullPointerException if the worker has not a oldPosition or a positionOccupied */
    boolean hasWin(int workerIndex) throws NullPointerException;

    /* Method that returns a list with max 2 elements which contains the indexes of the workers blocked of this Player
    * Throws NullPointerException if a worker has not a positionOccupied */
    List<Integer> blockedWorkers() throws  NullPointerException;

    /* Method that returns true if the worker number workerIndex cannot build in any position, false otherwise
    *  Throws NullPointerException if worker has not a positionOccupied
    *  Throws InvalidIndexWorkerException if workerIndex is illegal */
    boolean isBlockedBuilding(int workerIndex) throws NullPointerException, InvalidIndexWorkerException;
}
