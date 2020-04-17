package it.polimi.ingsw;

public class InvalidIncrementLevelException extends RuntimeException {
    //Runtime exception which is thrown when someone call Cell::incrementLevel() on a occupied cell
    public InvalidIncrementLevelException(int r, int c) {
        super("You cannot build in cell : [" + r + "][" + c + "]");
    }
}
