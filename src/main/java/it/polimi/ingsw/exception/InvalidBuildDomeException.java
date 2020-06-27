package it.polimi.ingsw.exception;

/**
 * Exception thrown when there is already a dome in the position [row][col] selected
 * */
public class InvalidBuildDomeException extends RuntimeException {
    public InvalidBuildDomeException(Integer row, Integer col) {
        super("There already is a dome in position [" + row + "][" + col + "]");
    }
}
