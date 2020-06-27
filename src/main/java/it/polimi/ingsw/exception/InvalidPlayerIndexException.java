package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Exception thrown when is tried to use a non valid player index
 * */
public class InvalidPlayerIndexException extends RuntimeException {
    public InvalidPlayerIndexException(PlayerIndex index) {
        super("There isn't a player with PlayerIndex " + index);
    }
}
