package it.polimi.ingsw.exception;

/**
 * Exception thrown when gods chosen by godLike are not enough cause players are more
 * */
public class NotEnoughGodsForPlayerException extends RuntimeException{

    public NotEnoughGodsForPlayerException(int diff){
        super("There aren't enough gods for player, missed: " + diff);
    }
}
