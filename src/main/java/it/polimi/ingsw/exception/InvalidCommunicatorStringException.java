package it.polimi.ingsw.exception;

public class InvalidCommunicatorStringException extends RuntimeException {
    public InvalidCommunicatorStringException(String whatCommunicator, String name) {
        super("You can't have a " + whatCommunicator + " named " + name);
    }
}
