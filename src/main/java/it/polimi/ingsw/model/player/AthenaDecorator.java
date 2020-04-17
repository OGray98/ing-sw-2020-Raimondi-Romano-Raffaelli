package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.deck.God;

public class AthenaDecorator extends PlayerOpponentTurnDecorator {

    private God godName;
    private String godDescription;

    public AthenaDecorator(){
        this.godName = God.ATHENA;
        this.godDescription = God.ATHENA.GetGodDescription();
    }

    public AthenaDecorator(PlayerInterface player){ super(player);}


    @Override
    public God getGodName() {
        return this.godName;
    }

    @Override
    public String getGodDescription(){
        return this.godDescription;
    }
}
