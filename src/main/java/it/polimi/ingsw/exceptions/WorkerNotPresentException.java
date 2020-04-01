package it.polimi.ingsw.exceptions;

public class WorkerNotPresentException extends RuntimeException {
    public WorkerNotPresentException(int r, int c) {
        super("There is not a worker in position: [" + r + "][" + c + "]");
    }
}
