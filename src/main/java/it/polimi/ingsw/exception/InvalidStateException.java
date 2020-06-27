package it.polimi.ingsw.exception;

import it.polimi.ingsw.controller.GameState;

/**
 * Exception thrown when user try to make an action in a non valid state for that action
 * */
public class InvalidStateException extends RuntimeException {
    public InvalidStateException(GameState rightState, GameState currentState) {
        super("You are in " + currentState + ", not in " + rightState);
    }
}
