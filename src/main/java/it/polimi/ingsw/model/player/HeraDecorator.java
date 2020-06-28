package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;

/**
 * Class that decorates Player. It is used to play Hera
 * */
public class HeraDecorator extends PlayerOpponentTurnDecorator{

    public HeraDecorator() {
        super("Hera", "An opponent cannot win by moving into a perimeter space.", GameState.MATCH_ENDED, GameState.MATCH_ENDED);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
