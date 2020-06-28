package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

/**
 * Abstract class to group gods with powers that have effects on other players turn
 * */
public abstract class PlayerOpponentTurnDecorator extends PlayerDecorator {
    public PlayerOpponentTurnDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }


}
