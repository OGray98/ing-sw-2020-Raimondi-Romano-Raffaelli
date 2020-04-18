package it.polimi.ingsw;

public class InvalidNumberCardsChosenException extends RuntimeException {
    public InvalidNumberCardsChosenException(int numPlayers, int numCards) {
        super("There are " + numPlayers + " players, you chose " + numCards + "cards");
    }
}