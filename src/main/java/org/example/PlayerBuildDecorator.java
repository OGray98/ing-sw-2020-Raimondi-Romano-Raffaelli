package org.example;

public class PlayerBuildDecorator extends PlayerDecorator {
    public PlayerBuildDecorator(PlayerInterface player, String godName, String godDescription) {
        super(player, godName, godDescription);
    }
}
