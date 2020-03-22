package it.polimi.ingsw;

public interface PlayerInterface {

    //Getter of nickName
    public String getNickName();

    //Get the godChosen of the Player
    public God getGodChosen() throws Exception;

    //TODO: aggiunta a UML
    //Get the worker number workerIndex
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    public Worker getWorker(int workerIndex) throws ArrayIndexOutOfBoundsException;

    //Get the Position/Cell of the Worker tile
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    public Position getWorkerPosition(int workerIndex) throws ArrayIndexOutOfBoundsException;

    //Move the Worker tile on the Cell wanted
    public void moveWorker(Board map, Position newCellPosition,int workerIndex) throws ArrayIndexOutOfBoundsException;

    //Build with the Worker tile in the Cell wanted
    //Throws IllegalArgumentException if workerIndex < 0 || workerIndex > 1
    public void build(Board map, Position newBuildingPosition, int workerIndex) throws ArrayIndexOutOfBoundsException;

    //Set godChosen of Player with a God at index godChosenNum in the chosenCards[] array of the Deck
    //Throws IllegalArgumentException if godChosenNum < 0 || godChosenNum > 2
    public void chooseGodPower(God godChosen) throws IllegalArgumentException;

    //TODO: aggiunta a UML
    //Put a Worker on the map during the first round
    public void putWorker(Cell startingCell, int workerIndex) throws ArrayIndexOutOfBoundsException;
}
