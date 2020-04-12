package org.example;

import java.util.List;
import java.util.Map;

public class PrometheusDecorator extends PlayerYourTurnDecorator {


    public PrometheusDecorator(){
        String godName = "Prometheus";
        super.setGodName(godName);
        String description = "If your Worker does not move up, it may build both before and after moving.";
        super.setGodDescription(description);
    }

    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }


   

    @Override
    public void move(Cell newOccupiedCell) throws NullPointerException {
        super.setActivePower(false);
        super.move(newOccupiedCell);
    }

    @Override
    public boolean canMove(List<Cell> adjacentCells, Map<Position,PlayerIndex> adjacentPlayerList, Position movePos) throws InvalidPositionException{
        if(movePos.col > 4 || movePos.row > 4 || movePos.col < 0 || movePos.row < 0) throw new InvalidPositionException(movePos.row, movePos.col);
        if(adjacentCells == null) throw new NullPointerException("adjacentCells is null!");
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if(p.equals(movePos)) return false;
        }

        for(Cell cell : adjacentCells){
            if(cell.getPosition().equals(movePos)){
                //check if there is a dome
                if(cell.hasDome()) return false;
                //if cantGoUp is true, check if it is a level up move
                if(super.getCantGoUp()){
                    if(cell.getLevel() > super.getCellOccupied().getLevel()) return false;
                }
                if(super.getActivePower()){
                    return cell.getLevel() - super.getCellOccupied().getLevel() <= 0;
                }
                return cell.getLevel() - super.getCellOccupied().getLevel() <= 1;
            }
        }
        return false;
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition) {
        if(super.getActivePower() && super.canBuild(adjacentList, adjacentPlayerList, powerPosition))
            return true;
        return false;
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition) {
        return new BoardChange(powerPosition,BuildType.LEVEL);
    }
}
