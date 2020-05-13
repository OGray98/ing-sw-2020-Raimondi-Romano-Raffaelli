package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.ControllableByClientMessage;

public class PongMessage extends Message implements MessageToServer {

    public PongMessage() {
        super(null, TypeMessage.PONG);
    }

    @Override
    public String toString() {
        return "Pong message from client";
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {

    }
}
