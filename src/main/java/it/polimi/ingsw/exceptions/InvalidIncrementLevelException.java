package it.polimi.ingsw.exceptions;

public class InvalidIncrementLevelException extends Exception{
    public InvalidIncrementLevelException(int r, int c){
        super("You cannot build in cell : [" + r +"][" + c + "]");
    }
}