package it.polimi.ingsw.exception;

public class NotEnoughGodsForPlayerException extends RuntimeException{

    public NotEnoughGodsForPlayerException(int diff){
        super("There aren't enough gods for player, missed: " + diff);
    }
}
