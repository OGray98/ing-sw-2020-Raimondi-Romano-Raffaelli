package it.polimi.ingsw;

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
