package it.polimi.ingsw.exception;

public class MaxPlayersException extends RuntimeException {
    public MaxPlayersException(){
        super("Max number of players reached! Impossible to add other players!");
    }
}
