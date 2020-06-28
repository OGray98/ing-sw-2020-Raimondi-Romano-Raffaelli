package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

/**
 * Abstract class to group gods with powers available in their own turn
 * */
public abstract class PlayerYourTurnDecorator extends PlayerDecorator {
    public PlayerYourTurnDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }


}
