package org.example;

public class PlayerOpponentTurnDecorator extends PlayerDecorator {
    public PlayerOpponentTurnDecorator(PlayerInterface player, String godName, String godDescription) {
        super(player, godName, godDescription);
    }
}
