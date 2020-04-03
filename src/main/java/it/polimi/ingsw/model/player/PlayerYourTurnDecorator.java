package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.player.PlayerDecorator;

public abstract class PlayerYourTurnDecorator extends PlayerDecorator {

    public PlayerYourTurnDecorator(PlayerInterface player){ super(player);}
}
