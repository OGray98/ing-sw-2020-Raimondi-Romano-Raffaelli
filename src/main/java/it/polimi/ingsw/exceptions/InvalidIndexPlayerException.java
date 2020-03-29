package it.polimi.ingsw.exceptions;

public class InvalidIndexPlayerException extends RuntimeException {
    //Runtime exception which is thrown when someone want to access to a position not in the range [0][0]- [4][4]
    public InvalidIndexPlayerException(int i) {
        super("You cannot have a player with index: " + i);
    }
}
