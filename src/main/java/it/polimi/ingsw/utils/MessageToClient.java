package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;

public interface MessageToClient {
    void execute(ControllableByServerMessage controllable) throws NullPointerException;

    TypeMessage getType();
}
