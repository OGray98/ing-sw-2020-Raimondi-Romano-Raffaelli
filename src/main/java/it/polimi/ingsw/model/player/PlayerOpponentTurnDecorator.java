package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.PlayerDecorator;

public abstract class PlayerOpponentTurnDecorator extends PlayerDecorator {

    public PlayerOpponentTurnDecorator(PlayerInterface player){ super(player);}
}
