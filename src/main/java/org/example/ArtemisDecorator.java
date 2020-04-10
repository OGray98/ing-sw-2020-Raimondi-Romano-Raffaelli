package org.example;

import java.util.List;
import java.util.Map;

public class ArtemisDecorator extends PlayerMoveDecorator {

    public ArtemisDecorator(){
        String godName = "Artemis";
        super.setGodName(godName);
        String description = "Your Worker may move one additional time, but not back to its initial space.";
        super.setGodDescription(description);
    }

    @Override
    public void move(Cell newOccupiedCell){
        super.setActivePower(true);
        super.move(newOccupiedCell);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition){
        return super.canMove(adjacentList, adjacentPlayerList, powerPosition) && !powerPosition.equals(super.getOldCell());
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition){
        super.setActivePower(false);
        return new BoardChange(super.getCellOccupied().getPosition(), powerPosition, super.getPlayerNum());
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
