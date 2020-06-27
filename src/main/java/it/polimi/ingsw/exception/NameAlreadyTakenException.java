package it.polimi.ingsw.exception;

/**
 * Exception thrown when a user try to use a nickname already taken
 * */
public class NameAlreadyTakenException extends RuntimeException {
    public NameAlreadyTakenException(){
        super("This name is not available!");
    }
}
