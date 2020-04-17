package it.polimi.ingsw;

public class InvalidBuildDomeException extends RuntimeException {
    public InvalidBuildDomeException(Integer row, Integer col) {
        super("There already is a dome in position [" + row + "][" + col + "]");
    }
}
