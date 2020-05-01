package it.polimi.ingsw.exception;

import it.polimi.ingsw.controller.GameState;

public class InvalidStateException extends RuntimeException {
    public InvalidStateException(GameState rightState, GameState currentState) {
        super("You are in " + currentState + ", not in " + rightState);
    }
}
