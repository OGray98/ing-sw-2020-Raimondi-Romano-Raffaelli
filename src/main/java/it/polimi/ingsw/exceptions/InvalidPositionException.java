package it.polimi.ingsw.exceptions;

public class InvalidPositionException extends RuntimeException {
    //Runtime exception which is thrown when someone want to access to a position not in the range [0][0]- [4][4]
    public InvalidPositionException(int r, int c) {
        super("You cannot have a position in : [" + r + "][" + c + "]");
    }
}
