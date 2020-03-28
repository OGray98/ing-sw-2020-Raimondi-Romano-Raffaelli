package it.polimi.ingsw.model;

public class Worker {

    private Position positionOccupied;
    private final CellOccupation playerNum; //final perchè la pedina rimane dello stesso giocatore sempre

    //Constructor where it is set the player who own this worker
    public Worker(CellOccupation playerNum){
        //playerNum must be one between: PLAYER1, PLAYER2, PLAYER3!
        this.playerNum = playerNum;
    }

    public Position getPositionOccupied() {
        return positionOccupied;
    }

    public CellOccupation getPlayerNum() {
        return playerNum;
    }

    //Set the Position occupied by this Worker, update on the board will be done by the controller
    public void setPositionOccupied(Position positionOccupied){
        this.positionOccupied = positionOccupied;
    }

    //Method that move the worker in newWorkerPosition, update on the board will be done by the controller
    //TODO:Forse da aggiungere controllo della win condition???
    public void move(Position newWorkerPosition){
        this.setPositionOccupied(newWorkerPosition);
    }

    /*
    * Old method build
    * Method that try to build in buildingPosition, it handles Exception from Cell.incrementLevel()
    public void build(Position buildingCell){

        try{
            buildingCell.incrementLevel();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }*/

    @Override
    public String toString(){
        return "Worker of Player: "
                + this.playerNum;
    }
}
