package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.model.player.PlayerInterface;

public interface CardInterface {

    PlayerInterface setPlayer(PlayerInterface player);

    void setChosenGod(Boolean condition);

    boolean getBoolChosenGod();

    String getGodName();

    String getGodDescription();




}
