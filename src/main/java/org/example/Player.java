package org.example;

import java.util.List;
import java.util.Map;

public class Player implements PlayerInterface{

    private String nickName;
    private PlayerIndex playerNum;
    private Cell oldCell;
    private Cell cellOccupied;
    private boolean cantGoUp;
    private boolean activePower;


    public Player(String nickName, PlayerIndex playerNum){
        this.nickName = nickName;
        this.playerNum = playerNum;
        this.cantGoUp = false;
        this.activePower = false;

    }

    /*Set values of the situation after the first insert of a worker*/
    @Override
    public void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp){
        this.cellOccupied = cellOccupied;
        this.cantGoUp = cantGoUp;
    }

    /*Set values of the current information about the selected tile*/
    @Override
    public void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp){
        this.oldCell = oldCell;
        this.cellOccupied = cellOccupied;
        this.cantGoUp = cantGoUp;
    }

    /*Set values of the situation after a move*/
    @Override
    public void setAfterMove(Cell oldCell, Cell cellOccupied){
        this.oldCell = oldCell;
        this.cellOccupied = cellOccupied;
    }

    @Override
    public void setActivePower(boolean isPowerOn){
        this.activePower = isPowerOn;
    }

    @Override
    public void setCantGoUp(boolean cantGoUp){
        this.cantGoUp = cantGoUp;
    }

    @Override
    public boolean canMove(Map<Position,PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException{
        if(moveCell.getPosition().col > 4 || moveCell.getPosition().row > 4 || moveCell.getPosition().col < 0 || moveCell.getPosition().row < 0) throw new InvalidPositionException(moveCell.getPosition().row, moveCell.getPosition().col);
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if(p.equals(moveCell.getPosition())) return false;
        }

        if(this.cellOccupied.getPosition().isAdjacent(moveCell.getPosition())){
            //check if there is a dome
            if(moveCell.hasDome()) return false;
            //if cantGoUp is true, check if it is a level up move
            if(this.cantGoUp){
                if(moveCell.getLevel() > this.cellOccupied.getLevel()) return false;
            }
            return moveCell.getLevel() - this.cellOccupied.getLevel() <= 1;
        }

        /*for(Cell cell : adjacentCells){
            if(cell.getPosition().equals(movePos)){
                //check if there is a dome
                if(cell.hasDome()) return false;
                //if cantGoUp is true, check if it is a level up move
                if(cantGoUp){
                    if(cell.getLevel() > this.cellOccupied.getLevel()) return false;
                }
                return cell.getLevel() - this.cellOccupied.getLevel() <= 1;
            }
        }*/
        return false;
    }

    @Override
    public boolean canBuild(List<Cell> adjacentCells, Map<Position, PlayerIndex> adjacentPlayerList, Position buildPos){
        if(buildPos.col > 4 || buildPos.row > 4 || buildPos.col < 0 || buildPos.row < 0) throw new InvalidPositionException(buildPos.row, buildPos.col);
        if(adjacentCells == null) throw new NullPointerException("adjacentCells is null!");
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        //check if the cell is occupied by a player
        for(Position p : adjacentPlayerList.keySet()){
            if(p.equals(buildPos)) return false;
        }

        for(Cell cell : adjacentCells){
            if(cell.getPosition().equals(buildPos)){
                //check if the cell has a dome
                if(!cell.hasDome()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasWin() throws NullPointerException{
        if(this.oldCell == null) throw new NullPointerException("Worker never moved yet!");
        if(this.cellOccupied == null) throw new NullPointerException("Worker has not a cell occupation!");

        return this.oldCell.getLevel() == 2 && this.cellOccupied.getLevel() == 3;
    }

    @Override
    public void move(Cell newOccupiedCell) throws NullPointerException{
        if(newOccupiedCell == null) throw new NullPointerException("newOccupiedCell is null!");
        if(this.cellOccupied == null) throw new NullPointerException("cellOccupied is null!");

        this.oldCell = this.cellOccupied;
        this.cellOccupied = newOccupiedCell;
    }

    @Override
    public void activePowerAfterBuild(){
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell){
        return false;
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell){
        return null;
    }

    @Override
    public Cell getOldCell() throws NullPointerException{
        if(this.cellOccupied == null) throw new NullPointerException("Worker has not an old cell");

        return new Cell(this.oldCell);
    }

    @Override
    public Cell getCellOccupied() throws NullPointerException{
        if(this.cellOccupied == null) throw new NullPointerException("Worker has not a cell occupied");

        return new Cell(this.cellOccupied);
    }

    @Override
    public boolean getActivePower(){
        return this.activePower;
    }

    @Override
    public boolean getCantGoUp(){
        return this.cantGoUp;
    }

    @Override
    public PlayerIndex getPlayerNum(){
        return this.playerNum;
    }

    @Override
    public String toString(){
        return "Player nickname: "
                + this.nickName;
    }
}
