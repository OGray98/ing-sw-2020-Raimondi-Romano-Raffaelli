package it.polimi.ingsw.exception;

/**
 * Exception thrown when a player has less of two worker (it should not happens)
 * */
public class MissingWorkerException extends RuntimeException {
    public MissingWorkerException(int workersNumber){
        super("A player has" + workersNumber + "workers on the map, must be 2");
    }
}
