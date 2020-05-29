package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

public class InvalidPlayerIndexException extends RuntimeException {
    public InvalidPlayerIndexException(PlayerIndex index) {
        super("There isn't a player with PlayerIndex " + index);
    }
}
