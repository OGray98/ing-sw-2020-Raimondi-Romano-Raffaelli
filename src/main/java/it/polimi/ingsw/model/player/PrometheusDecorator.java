package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.deck.God;

public class PrometheusDecorator extends PlayerYourTurnDecorator {

    private God godName;
    private String godDescription;

    public PrometheusDecorator(){
        this.godName = God.PROMETHEUS;
        this.godDescription = God.PROMETHEUS.GetGodDescription();
    }

    public PrometheusDecorator(PlayerInterface player){ super(player);}


    @Override
    public God getGodName() {
        return this.godName;
    }

    @Override
    public String getGodDescription() {
        return this.godDescription;
    }
}
