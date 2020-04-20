package it.polimi.ingsw.exception;

public class NotPutTwoWorkerInSamePositionException extends RuntimeException{

    public NotPutTwoWorkerInSamePositionException(int r,int c){
        super("You can't put the workers in same position : [" + r + "][" + c + "]");
    }
}
