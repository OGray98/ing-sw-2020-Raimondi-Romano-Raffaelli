package it.polimi.ingsw.exception;

public class FullChosenCardsException extends RuntimeException {

    public FullChosenCardsException(){ super("The deck of chosen god is full");}
}
