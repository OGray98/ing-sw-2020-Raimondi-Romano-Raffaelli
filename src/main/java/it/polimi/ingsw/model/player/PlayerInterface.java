package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

import java.util.List;

public interface PlayerInterface {

    //Getter of the game board
    Board getBoard();

    //Getter of the selectedWorker
    int getSelectedWorker();

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

    //Set the selectedWorker, it will be the same for an entire turn ( after the move )
    void setSelectedWorker(int selectedWorker);

    void putWorker(Position startingCellPosition, int workerIndex) throws InvalidIndexWorkerException, InvalidPositionException;

    /* Move the selected Worker tile on the Position wanted
    * Throws InvalidPositionException if newCellPosition is not a legal position
    * Throws NotSelectedWorkerException if user has not selected any worker */
    void moveWorker(Position newCellPosition) throws InvalidPositionException, NotSelectedWorkerException;

    /* General check of possible moves, not specialized in particular Gods!
    * worker considered is the worker selected by the user
    * If return value == true, Controller will procede with the calling of moveWorker() method
    * If return value == false, Controller must notify to the user to select an other Cell for the move phase
    * Throws InvalidPositionException if the Position moveToCheck is not a possible position
    * Throws IllegalArgumentException if an invalid workerLevel (0 <= workerLevel <= 3)
    * Throws NotSelectedWorkerException if user has not selected any worker */
    boolean canMove(Position moveToCheck) throws InvalidPositionException, IllegalArgumentException, NotSelectedWorkerException;

    //Build on the given Position toBuildPosition
    void buildWorker(Position toBuildPosition);

    /* General check of possible build, not specialized in particular Gods!
    * It checks possible builds on the cells adjacent to the worker selected by the user
    * Throws InvalidPositionException if the Position buildingPosition is not a possible position
    * Throws NotSelectedWorkerException if user has not selected any worker */
    boolean canBuild(Position buildingPosition) throws InvalidPositionException, NotSelectedWorkerException;

    /* Set godChosen of Player with a God at index godChosenNum in the chosenCards[] array of the Deck
    * Throws IllegalArgumentException if godChosenNum < 0 || godChosenNum > 2 */
    void chooseGodPower(God godChosen) throws IllegalArgumentException;

    //Verify from view if player want to use power
    boolean canUsePower(Position pos);

    //In a decorated PlayerDecorator, it implements the power related to the God chosen by the player
    void usePower(Position pos);

    /* Method that requires to be called after a moveWorker() ( not a putWorker()! ) and before a buildWorker() operation
    * It returns true if a Player moved the worker selected in the turn from a level 2 cell to a level 3 cell ( win condition )
    * It returns false in all other cases
    * Throws NullPointerException if the worker has not a oldPosition or a positionOccupied
    * Throws NotSelectedWorkerException if user has not selected any worker */
    boolean hasWin() throws NullPointerException, NotSelectedWorkerException;

    /* Method that returns a list with max 2 elements which contains the indexes of the workers blocked of this Player
    * Throws NullPointerException if a worker has not a positionOccupied */
    List<Integer> blockedWorkers() throws  NullPointerException;

    /* Method that returns true if the worker number workerIndex cannot build in any position, false otherwise
    *  Throws NullPointerException if worker has not a positionOccupied
    *  Throws NotSelectedWorkerException if user has not selected any worker */
    boolean isBlockedBuilding() throws NullPointerException, NotSelectedWorkerException;
}
