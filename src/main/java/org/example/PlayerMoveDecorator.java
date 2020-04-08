package org.example;

public class PlayerMoveDecorator extends PlayerDecorator {
    public PlayerMoveDecorator(PlayerInterface player, String godName, String godDescription) {
        super(player, godName, godDescription);
    }
}
