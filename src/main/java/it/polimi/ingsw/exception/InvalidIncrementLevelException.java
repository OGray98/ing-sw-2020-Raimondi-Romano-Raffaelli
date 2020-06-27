package it.polimi.ingsw.exception;

/**
 * Exception thrown when player try to build in a cell occupied
 * */
public class InvalidIncrementLevelException extends RuntimeException {
    //Runtime exception which is thrown when someone call Cell::incrementLevel() on a occupied cell
    public InvalidIncrementLevelException(int r, int c) {
        super("You cannot build in cell : [" + r + "][" + c + "]");
    }
}
