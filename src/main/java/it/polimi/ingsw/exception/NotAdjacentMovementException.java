package it.polimi.ingsw.exception;

/**
 * Exception thrown when a player try to move on a position not adjacent
 * */
public class NotAdjacentMovementException extends RuntimeException {
    public NotAdjacentMovementException(int rOld, int cOld, int rNew, int cNew) {
        super("You can't move a player from Position :[" + rOld + "][" + cOld + "]" +
                "to Position : [" + rNew + "][" + cNew + "]");
    }
}
