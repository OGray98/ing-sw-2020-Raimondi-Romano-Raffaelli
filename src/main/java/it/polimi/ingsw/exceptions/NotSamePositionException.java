package it.polimi.ingsw.exceptions;

public class NotSamePositionException extends RuntimeException {

    //Exception invoked when player choose a different position
    public NotSamePositionException (int r,int c){super("You can't build in: [" + r + "][" + c + "] is a different position");}

}
