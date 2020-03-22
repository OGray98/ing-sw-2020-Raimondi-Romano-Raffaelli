package it.polimi.ingsw;

public class Worker {

    private Cell cellOccupied;
    private final CellOccupation playerNum; //final perchè la pedina rimane dello stesso giocatore sempre

    //Constructor where it is set the player who own this worker
    public Worker(CellOccupation playerNum){
        //playerNum must be one between: PLAYER1, PLAYER2, PLAYER3!
        this.playerNum = playerNum;
    }

    public Cell getCellOccupied() {
        return cellOccupied;
    }

    public CellOccupation getPlayerNum() {
        return playerNum;
    }

    //Set the Cell occupied by this Worker and set the related CellOccupation
    public void setCellOccupied(Cell cellOccupied){
        this.cellOccupied = cellOccupied;
        this.cellOccupied.setOccupation(playerNum);
    }

    //Method that move the worker in newWorkerCell
    //TODO:Forse da aggiungere controllo della win condition???
    public void move(Cell newWorkerCell){

        this.cellOccupied.setOccupation(CellOccupation.EMPTY);
        this.setCellOccupied(newWorkerCell);

    }

    //Method that try to build in buildingPosition, it handles Exception from Cell.incrementLevel()
    public void build(Cell buildingCell){

        try{
            buildingCell.incrementLevel();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String toString(){
        return "Worker of Player: "
                + this.playerNum;
    }
}
