package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

public abstract class PlayerOpponentTurnDecorator extends PlayerDecorator {
    public PlayerOpponentTurnDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }


}
