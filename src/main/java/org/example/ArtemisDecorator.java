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
    public int getPowerListDimension() {
        return 1;
    }

    @Override
    public void move(Cell newOccupiedCell){
        super.setActivePower(true);
        super.move(newOccupiedCell);
    }

    @Override
    public boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException {
        super.setActivePower(false);
        return super.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canMove(adjacentPlayerList, adjacentList.get(0)) && !adjacentList.get(0).equals(super.getOldCell());
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        super.setActivePower(false);
        return new BoardChange(super.getCellOccupied().getPosition(), powerCell.getPosition(), super.getPlayerNum());
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
