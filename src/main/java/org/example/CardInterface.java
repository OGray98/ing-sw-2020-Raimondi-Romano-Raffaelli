package org.example;

public interface CardInterface {

    PlayerInterface setPlayer(PlayerInterface player);

    void setChosenGod(Boolean condition);

    boolean getBoolChosenGod();

    String getGodName();

    String getGodDescription();

    void setGodName(String nome);

    void setGodDescription(String description);
}
