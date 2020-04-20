package it.polimi.ingsw.exception;

public class NotAdjacentBuildingException extends RuntimeException {
    public NotAdjacentBuildingException(int rWorker, int cWorker, int rBuild, int cBuild) {
        super("You can't build with a player in Position :[" + rWorker + "][" + cWorker + "]" +
                "to Position : [" + rBuild + "][" + cBuild + "]");
    }
}
