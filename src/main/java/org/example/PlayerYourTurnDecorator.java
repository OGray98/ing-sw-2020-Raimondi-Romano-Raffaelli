package org.example;

public class PlayerYourTurnDecorator extends PlayerDecorator {
    public PlayerYourTurnDecorator(PlayerInterface player, String godName, String godDescription) {
        super(player, godName, godDescription);
    }
}
