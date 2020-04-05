package it.polimi.ingsw.exceptions;

/* Exception thrown if an operation is called without a worker index selected */
public class NotSelectedWorkerException extends RuntimeException {
    public NotSelectedWorkerException(){
        super("No Worker is selected for this operation!");
    }
}
