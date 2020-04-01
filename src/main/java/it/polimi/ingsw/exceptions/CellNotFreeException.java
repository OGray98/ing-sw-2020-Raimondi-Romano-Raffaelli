package it.polimi.ingsw.exceptions;

public class CellNotFreeException extends RuntimeException {
    //Runtime exception which is thrown when someone want to move or create in a occupiedCell
    public CellNotFreeException(int r, int c) {
        super("Cell in position [" + r + "][" + c + "] isn't free");
    }
}
