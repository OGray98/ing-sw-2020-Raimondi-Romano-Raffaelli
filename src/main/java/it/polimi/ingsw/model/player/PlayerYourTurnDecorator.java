package it.polimi.ingsw.model.player;



public abstract class PlayerYourTurnDecorator extends PlayerDecorator {

    public PlayerYourTurnDecorator(){}

    public PlayerYourTurnDecorator(PlayerInterface player){ super(player);}
}
