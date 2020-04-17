package it.polimi.ingsw.exception;

import it.polimi.ingsw.model.player.PlayerIndex;

public class InvalidPutWorkerException extends RuntimeException {
    //Runtime exception which is thrown when someone want to put more that two worker
    public InvalidPutWorkerException(int r, int c, PlayerIndex index) {
        super(index + " cannot put a worker in : [" + r + "][" + c + "], because he already have 2 worker in your board");
    }
}
