package it.polimi.ingsw.exception;

/**
 * Exception thrown when the number of cards chosen is different from the number of player
 * */
public class InvalidNumberCardsChosenException extends RuntimeException {
    public InvalidNumberCardsChosenException(int numPlayers, int numCards) {
        super("There are " + numPlayers + " players, you chose " + numCards + "cards");
    }
}
