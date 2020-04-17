package it.polimi.ingsw;

public class MissingWorkerException extends RuntimeException {
    public MissingWorkerException(int workersNumber){
        super("A player has" + workersNumber + "workers on the map, must be 2");
    }
}
