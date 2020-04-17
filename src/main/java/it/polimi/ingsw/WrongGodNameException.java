package it.polimi.ingsw;

public class WrongGodNameException extends RuntimeException {
    public WrongGodNameException(String name) {
        super("There isn't a god named " + name);
    }
}
