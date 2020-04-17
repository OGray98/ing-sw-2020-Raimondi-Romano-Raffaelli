package it.polimi.ingsw.exception;

public class AlreadyPresentGameException extends RuntimeException {
    public AlreadyPresentGameException() {
        super("There already is a game instance");
    }
}
