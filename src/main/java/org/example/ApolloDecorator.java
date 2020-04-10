package org.example;

import java.util.List;
import java.util.Map;

public class ApolloDecorator extends PlayerMoveDecorator{


    public ApolloDecorator(){
        String godName = "Apollo";
        super.setGodName(godName);
        String description = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
        super.setGodDescription(description);
    }



    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }


    @Override
    public boolean canMove(List<Cell> adjacentCells, Map<Position,PlayerIndex> adjacentPlayerList, Position movePos) throws InvalidPositionException{
        if(movePos.col > 4 || movePos.row > 4 || movePos.col < 0 || movePos.row < 0) throw new InvalidPositionException(movePos.row, movePos.col);
        if(adjacentCells == null) throw new NullPointerException("adjacentCells is null!");
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");



        for(Cell cell : adjacentCells){
            if(cell.getPosition().equals(movePos)){
                //check if there is a dome
                if(cell.hasDome()) return false;
                //if cantGoUp is true, check if it is a level up move
                if(super.getCantGoUp()){
                    if(cell.getLevel() > super.getCellOccupied().getLevel()) return false;
                }
                for(Position p : adjacentPlayerList.keySet()){
                    //check if there is a player
                    if(p.equals(movePos) && (cell.getLevel() - super.getCellOccupied().getLevel()) <= 1/* && !adjacentPlayerList.get(movePos).equals(super)*/) {

                        }
                    }
                return cell.getLevel() - super.getCellOccupied().getLevel() <= 1;

                    }
        }
        return false;
    }


}
