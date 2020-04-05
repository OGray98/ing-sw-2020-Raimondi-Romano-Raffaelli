package it.polimi.ingsw.exceptions;

/*Exception thrown if it is asked a worker in a position but the position has not a worker inside */
public class WorkerNotPresentException extends RuntimeException {
    public WorkerNotPresentException(int r, int c) {
        super("There is not a worker in position: [" + r + "][" + c + "]");
    }
}
