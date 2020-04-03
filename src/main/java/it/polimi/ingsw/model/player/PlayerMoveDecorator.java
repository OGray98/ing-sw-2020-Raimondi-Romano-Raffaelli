package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.PlayerDecorator;

public abstract class PlayerMoveDecorator extends PlayerDecorator {

    public PlayerMoveDecorator(PlayerInterface player){ super(player);}
}
