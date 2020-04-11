package org.example;

public class NotPresentWorkerException extends RuntimeException {
    //Exception that is thrown when you want to operate on a player but the player isn't in the cell
    public NotPresentWorkerException(int r, int c) {
        super("There isn't a player in : [" + r + "][" + c + "]");
    }

    public NotPresentWorkerException(int r, int c, PlayerIndex index) {
        super("There isn't a worker of " + index + " in : [" + r + "][" + c + "]");
    }
}
