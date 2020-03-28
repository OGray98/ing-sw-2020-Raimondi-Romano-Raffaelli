package it.polimi.ingsw.model;

import java.util.List;

public interface PlayerInterface {

    //Getter of nickName
    String getNickName();

    //Get the godChosen of the Player
    God getGodChosen() throws NullPointerException;

    //Get the worker number workerIndex
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    Worker getWorker(int workerIndex) throws ArrayIndexOutOfBoundsException;

    //Get the Position of the Worker tile
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    Position getWorkerPosition(int workerIndex) throws ArrayIndexOutOfBoundsException;

    //Move the Worker tile on the Position wanted
    void moveWorker(Position newCellPosition, int workerIndex) throws ArrayIndexOutOfBoundsException, IllegalArgumentException;

    //General check of possible moves, not specialized in particular Gods!
    //Controller must give the workerLevel (int)
    //If return value == true, Controller will procede with the calling of moveWorker() method
    //If return value == false, Controller must notify to the user to select an other Cell for the move phase
    boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel);

    //Build with the Worker tile in the Cell wanted
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws ArrayIndexOutOfBoundsException;

    //Set godChosen of Player with a God at index godChosenNum in the chosenCards[] array of the Deck
    //Throws IllegalArgumentException if godChosenNum < 0 || godChosenNum > 2
    void chooseGodPower(God godChosen) throws IllegalArgumentException;

    //Put a Worker on the map during the first round
    void putWorker(Position startingPosition, int workerIndex) throws ArrayIndexOutOfBoundsException, IllegalArgumentException;
}
