package it.polimi.ingsw.exception;

/**
 * Exception thrown when does not exist a god with the name supposed
 * */
public class WrongGodNameException extends RuntimeException {
    public WrongGodNameException(String name) {
        super("There isn't a god named " + name);
    }
}
