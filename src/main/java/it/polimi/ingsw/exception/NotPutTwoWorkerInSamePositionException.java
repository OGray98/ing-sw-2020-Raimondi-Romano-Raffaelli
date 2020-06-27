package it.polimi.ingsw.exception;

/**
 * Exception thrown when a player try to put a worker on the position of his other worker
 * */
public class NotPutTwoWorkerInSamePositionException extends RuntimeException{

    public NotPutTwoWorkerInSamePositionException(int r,int c){
        super("You can't put the workers in same position : [" + r + "][" + c + "]");
    }
}
