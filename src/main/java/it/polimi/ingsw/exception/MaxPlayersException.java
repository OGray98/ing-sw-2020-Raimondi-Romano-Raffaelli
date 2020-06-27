package it.polimi.ingsw.exception;

/**
 * Exception thrown when maximum number of player is reached is tried to add an other player
 * */
public class MaxPlayersException extends RuntimeException {
    public MaxPlayersException(){
        super("Max number of players reached! Impossible to add other players!");
    }
}
