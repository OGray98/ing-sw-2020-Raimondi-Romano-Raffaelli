package it.polimi.ingsw.exception;

/**
 * Exception thrown when player try to build in a position not adjacent
 * */
public class NotAdjacentBuildingException extends RuntimeException {
    public NotAdjacentBuildingException(int rWorker, int cWorker, int rBuild, int cBuild) {
        super("You can't build with a player in Position :[" + rWorker + "][" + cWorker + "]" +
                "to Position : [" + rBuild + "][" + cBuild + "]");
    }
}
