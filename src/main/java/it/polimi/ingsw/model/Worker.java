package it.polimi.ingsw.model;

public class Worker {

    private Position positionOccupied;
    private Position oldPosition;
    private final int playerIndex; //final perchè la pedina rimane dello stesso giocatore sempre

    //Constructor where it is set the player who own this worker
    public Worker(int playerNum){
        //playerNum must be one between: PLAYER1, PLAYER2, PLAYER3!
        this.playerIndex = playerNum;
    }

    public Position getPositionOccupied() {
        return positionOccupied;
    }

    public Position getOldPosition(){
        return this.oldPosition;
    }

    public int getPlayerNum() {
        return playerIndex;
    }

    //Set the Position occupied by this Worker, update on the board will be done by the controller
    public void move(Position positionOccupied){
        this.oldPosition = this.positionOccupied;
        this.positionOccupied = positionOccupied;
    }

    @Override
    public String toString(){
        return "Worker of Player number: "
                + this.playerIndex;
    }
}
