package it.polimi.ingsw.exception;

import it.polimi.ingsw.controller.TypeMessage;

public class InvalidTypeMessage extends RuntimeException {
    public InvalidTypeMessage(String classMessage, TypeMessage type) {
        super("A " + classMessage + " can't be of type: " + type);
    }
}
