package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

public abstract class PlayerMoveDecorator extends PlayerDecorator {
    public PlayerMoveDecorator(String godName, String description, GameState powerState, GameState nextState) {

        super(godName, description, powerState, nextState);
    }


}
