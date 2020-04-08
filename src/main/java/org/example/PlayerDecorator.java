package org.example;

public abstract class PlayerDecorator implements PlayerInterface {
    private PlayerInterface player;
    private String name;
    private String description;

    public PlayerDecorator(PlayerInterface player) {
        this.player = player;
    }
}
