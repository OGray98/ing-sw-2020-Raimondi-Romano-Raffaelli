package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

public class WrongAssociationViewPlayerException extends RuntimeException {
    public WrongAssociationViewPlayerException(PlayerIndex ofRemoteView, PlayerIndex client) {
        super("This is remote view of " + ofRemoteView + ", not of" + client);
    }
}
