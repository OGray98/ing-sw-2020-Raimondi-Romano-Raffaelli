package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * PutWorkerMessage extends Message and it contains two positions where the player
 * wants to put his workers at the start of the game
 */
public class PutWorkerMessage extends TwoPositionMessage {


    public PutWorkerMessage(PlayerIndex client, Position pos1, Position pos2) {
        super(client, TypeMessage.PUT_WORKER, pos1, pos2);
    }

    public Position getPositionOne() {
        return super.getPositions().get(0);
    }

    public Position getPositionTwo() {
        return super.getPositions().get(1);
    }
}
