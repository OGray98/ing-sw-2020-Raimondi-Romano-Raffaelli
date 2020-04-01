package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.WorkerNotPresentException;

import java.util.List;

public interface PlayerInterface {

    //Getter of nickName
    String getNickName();

    //Get the godChosen of the Player
    God getGodChosen() throws NullPointerException;

    //Get the worker number workerIndex
    //Throws InvalidIndexPlayerException if workerIndex < 0 || workerIndex > 1
    Worker getWorker(int workerIndex) throws InvalidIndexWorkerException;

    int getWorkerIndex(Position position) throws WorkerNotPresentException, InvalidPositionException;

    //Get the Position of the Worker tile
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    Position getWorkerPositionOccupied(int workerIndex) throws InvalidIndexPlayerException;

    void putWorker(Position startingCellPosition, int workerIndex) throws InvalidIndexWorkerException, InvalidPositionException;

    //Move the Worker tile on the Position wanted
    //Throws InvalidIndexPlayerException if worker index is not a possible value
    void moveWorker(Position newCellPosition, int workerIndex) throws InvalidIndexWorkerException;

    //General check of possible moves, not specialized in particular Gods!
    //Controller must give the workerLevel (int)
    //If return value == true, Controller will procede with the calling of moveWorker() method
    //If return value == false, Controller must notify to the user to select an other Cell for the move phase
    //Throws InvalidPositionException if the Position moveToCheck is not a possible position
    //Throws NullPointerException if a null list is given
    //Throws IllegalArgumentException if an invalid workerLevel is given (0 <= workerLevel <= 3)
    boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel) throws InvalidPositionException, NullPointerException, IllegalArgumentException;

    //Build on the given Position toBuildPosition
    void buildWorker(Position toBuildPosition);

    //Build with the Worker tile in the Cell wanted
    //Throws NullPointerException if a null list is given
    //Throws InvalidPositionException if the Position buildingPosition is not a possible position
    boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws InvalidPositionException, NullPointerException;

    //Set godChosen of Player with a God at index godChosenNum in the chosenCards[] array of the Deck
    //Throws IllegalArgumentException if godChosenNum < 0 || godChosenNum > 2
    void chooseGodPower(God godChosen) throws IllegalArgumentException;
}
