package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;

import java.util.List;

public interface PlayerInterface {

    //Getter of nickName
    String getNickName();

    //Get the godChosen of the Player
    God getGodChosen() throws NullPointerException;

    //Get the worker number workerIndex
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    Worker getWorker(int workerIndex) throws InvalidIndexPlayerException;

    //Get the Position of the Worker tile
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    Position getWorkerPosition(int workerIndex) throws InvalidIndexPlayerException;

    //Move the Worker tile on the Position wanted
    void moveWorker(Position newCellPosition, int workerIndex) throws InvalidIndexPlayerException, IllegalArgumentException;

    //General check of possible moves, not specialized in particular Gods!
    //Controller must give the workerLevel (int)
    //If return value == true, Controller will procede with the calling of moveWorker() method
    //If return value == false, Controller must notify to the user to select an other Cell for the move phase
    //Throws NullPointerException if a null list is given
    //Throws IllegalArgumentException if an invalid workerLevel is given (0 <= workerLevel <= 3)
    boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel) throws NullPointerException, IllegalArgumentException;

    //Build with the Worker tile in the Cell wanted
    //Throws NullPointerException if a null list is given
    boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws NullPointerException;

    //Set godChosen of Player with a God at index godChosenNum in the chosenCards[] array of the Deck
    //Throws IllegalArgumentException if godChosenNum < 0 || godChosenNum > 2
    void chooseGodPower(God godChosen) throws IllegalArgumentException;

    //Put a Worker on the map during the first round
    void putWorker(Position startingPosition, int workerIndex) throws InvalidIndexPlayerException, IllegalArgumentException;
}
