package it.polimi.ingsw.exception;

/**
 * Exception thrown when is tried to use a god not present in the gods chosen by godLike
 * */
public class NotSelectedGodException extends RuntimeException {
    public NotSelectedGodException(String godName) {
        super("There isn't a selected god named " + godName);
    }
}
