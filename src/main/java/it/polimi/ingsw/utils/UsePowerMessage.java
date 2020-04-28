package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

public class UsePowerMessage extends TwoPositionMessage{

    public UsePowerMessage(PlayerIndex client, Position workerPos, Position powerPos){
        super(client, TypeMessage.USE_POWER, workerPos, powerPos);
        if(workerPos == null)
            throw new NullPointerException("workerPos");
        if(powerPos == null)
            throw new NullPointerException("powerPos");
    }

    public Position getWorkerPosition(){
        return super.getPositions().get(0);
    }

    public Position getPowerPosition(){
        return super.getPositions().get(1);
    }
}
