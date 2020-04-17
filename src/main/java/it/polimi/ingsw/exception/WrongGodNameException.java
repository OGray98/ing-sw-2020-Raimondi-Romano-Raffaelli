package it.polimi.ingsw.exception;

public class WrongGodNameException extends RuntimeException {
    public WrongGodNameException(String name) {
        super("There isn't a god named " + name);
    }
}
