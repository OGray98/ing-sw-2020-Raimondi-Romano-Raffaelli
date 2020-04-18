package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

public abstract class PlayerWinConditionDecorator extends PlayerDecorator {
    public PlayerWinConditionDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }


}
