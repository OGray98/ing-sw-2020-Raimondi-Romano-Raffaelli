package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.PlayerDecorator;

public abstract class PlayerWinConditionDecorator extends PlayerDecorator {

    public PlayerWinConditionDecorator(PlayerInterface player){ super(player);}
}
