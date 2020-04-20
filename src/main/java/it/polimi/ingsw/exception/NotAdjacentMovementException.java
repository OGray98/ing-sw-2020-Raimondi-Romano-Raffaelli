package it.polimi.ingsw.exception;

public class NotAdjacentMovementException extends RuntimeException {
    public NotAdjacentMovementException(int rOld, int cOld, int rNew, int cNew) {
        super("You can't move a player from Position :[" + rOld + "][" + cOld + "]" +
                "to Position : [" + rNew + "][" + cNew + "]");
    }
}
