package it.polimi.ingsw.model.player;



public abstract class PlayerWinConditionDecorator extends PlayerDecorator {

    public PlayerWinConditionDecorator(){}

    public PlayerWinConditionDecorator(PlayerInterface player){ super(player);}
}
