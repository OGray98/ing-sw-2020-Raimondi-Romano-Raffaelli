package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Exception thrown when a remote view does not corresponds to the related client
 * */
public class WrongAssociationViewPlayerException extends RuntimeException {
    public WrongAssociationViewPlayerException(PlayerIndex ofRemoteView, PlayerIndex client) {
        super("This is remote view of " + ofRemoteView + ", not of" + client);
    }
}
