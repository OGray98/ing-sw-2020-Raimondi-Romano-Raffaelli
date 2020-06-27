package it.polimi.ingsw.exception;

/**
 * Exception thrown when deck of chosen gods is full
 * */
public class FullChosenCardsException extends RuntimeException {

    public FullChosenCardsException(){ super("The deck of chosen god is full");}
}
