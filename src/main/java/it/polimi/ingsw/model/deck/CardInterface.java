package it.polimi.ingsw.model.deck;

import it.polimi.ingsw.model.player.PlayerInterface;

public interface CardInterface {

    //TODO: si può migliorare con metodo void
    PlayerInterface setPlayer(PlayerInterface player);

    void setChosenGod(Boolean condition);

    boolean getBoolChosenGod();

    String getGodName();

    String getGodDescription();

    void setGodName(String nome);

    void setGodDescription(String description);
}
