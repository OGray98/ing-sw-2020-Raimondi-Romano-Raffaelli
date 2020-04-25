package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

public class AlreadyPresentRemoteViewOfPlayerException extends RuntimeException {
    public AlreadyPresentRemoteViewOfPlayerException(PlayerIndex index) {
        super("There already is the remote view of " + index);
    }
}
