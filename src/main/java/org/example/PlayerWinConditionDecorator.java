package org.example;

public class PlayerWinConditionDecorator extends PlayerDecorator {
    public PlayerWinConditionDecorator(PlayerInterface player, String godName, String godDescription) {
        super(player, godName, godDescription);
    }
}
