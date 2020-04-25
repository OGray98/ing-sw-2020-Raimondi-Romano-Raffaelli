package it.polimi.ingsw.utils;

public enum TypeMessage {
    //Used in lobby
    NICKNAME, IS_THREE_PLAYERS_GAME,
    //Used in setup
    GODLIKE_CHOOSE_CARDS, SELECT_CARD,
    MOVE, USE_POWER, BUILD, GODLIKE_CHOOSE_FIRST_PLAYER,
    //Management message
    ERROR, ALREADY_SET_NICKNAME, CANT_CHOOSE_PLAYERS_NUMBER,
    NOT_BE_GOD_LIKE, WRONG_NUMBER_CARDS, NOT_YOUR_TURN
}
