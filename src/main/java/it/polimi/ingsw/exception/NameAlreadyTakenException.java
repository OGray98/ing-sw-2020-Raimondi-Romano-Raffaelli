package it.polimi.ingsw.exception;

public class NameAlreadyTakenException extends RuntimeException {
    public NameAlreadyTakenException(){
        super("This name is not available!");
    }
}
