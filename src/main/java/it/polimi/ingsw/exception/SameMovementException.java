package it.polimi.ingsw.exception;

/**
 * Exception thrown when someone call Cell::incrementLevel() on a occupied cell
 * */
public class SameMovementException extends RuntimeException {
    //Runtime exception which is thrown when someone call Cell::incrementLevel() on a occupied cell
    public SameMovementException(String message) {
        super("There is more thaan one movement with" + message);
    }
}
