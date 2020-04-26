package it.polimi.ingsw.utils;

import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * PutWorkerMessage extends Message and it contains two positions where the player
 * wants to put his workers at the start of the game
 * */
public class PutWorkerMessage extends Message{

    private final Position positionOne;
    private final Position positionTwo;

    public PutWorkerMessage(PlayerIndex client, Position posOne, Position posTwo){
        super(client, TypeMessage.PUTWORKER);
        if(posOne.col > 4 || posOne.row > 4 || posOne.col < 0 || posOne.row < 0) throw new InvalidPositionException(posOne.row, posOne.col);
        if(posTwo.col > 4 || posTwo.row > 4 || posTwo.col < 0 || posTwo.row < 0) throw new InvalidPositionException(posTwo.row, posTwo.col);
        this.positionOne = posOne;
        this.positionTwo = posTwo;
    }

    public Position getPositionOne(){
        return positionOne;
    }

    public Position getPositionTwo(){
        return positionTwo;
    }
}
