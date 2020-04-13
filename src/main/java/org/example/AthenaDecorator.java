package org.example;

import java.util.List;
import java.util.Map;

public class AthenaDecorator extends PlayerOpponentTurnDecorator {


    public AthenaDecorator(){
        String godName = "Athena";
        super.setGodName(godName);
        String description = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
        super.setGodDescription(description);
    }

    @Override
    public void move(Cell newOccupiedCell){
        if(newOccupiedCell.getLevel() > super.getCellOccupied().getLevel()){
            super.setActivePower(true);
        }
        super.move(newOccupiedCell);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell){
        return true;
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell){
        super.setActivePower(false);
        return new BoardChange(true);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
