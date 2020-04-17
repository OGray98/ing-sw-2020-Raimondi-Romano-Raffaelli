package it.polimi.ingsw.model.player;



public abstract class PlayerMoveDecorator extends PlayerDecorator {

    public PlayerMoveDecorator(){}

    public PlayerMoveDecorator(PlayerInterface player){ super(player);}
}
