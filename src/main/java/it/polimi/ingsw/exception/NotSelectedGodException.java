package it.polimi.ingsw.exception;

public class NotSelectedGodException extends RuntimeException {
    public NotSelectedGodException(String godName) {
        super("There isn't a selected god named " + godName);
    }
}
