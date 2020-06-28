package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

/**
 * Abstract class to group gods that have others win condition
 * */
public abstract class PlayerWinConditionDecorator extends PlayerDecorator {
    public PlayerWinConditionDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }


}
