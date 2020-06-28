package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

/**
 * Abstract class to group gods with build powers
 * */
public abstract class PlayerBuildDecorator extends PlayerDecorator {
    public PlayerBuildDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }




}
