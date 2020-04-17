package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.deck.God;

public class MinotaurDecorator extends PlayerMoveDecorator {

    private God godName;
    private String godDescription;

    public MinotaurDecorator(){
        this.godName = God.MINOTAUR;
        this.godDescription = God.MINOTAUR.GetGodDescription();
    }

    public MinotaurDecorator(PlayerInterface player){ super(player);}


    @Override
    public God getGodName() {
        return this.godName;
    }

    @Override
    public String getGodDescription() {
        return this.godDescription;
    }
}
