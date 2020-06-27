package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Exception thrown when there is already the remote view that is supposed to be created
 * */
public class AlreadyPresentRemoteViewOfPlayerException extends RuntimeException {
    public AlreadyPresentRemoteViewOfPlayerException(PlayerIndex index) {
        super("There already is the remote view of " + index);
    }
}
