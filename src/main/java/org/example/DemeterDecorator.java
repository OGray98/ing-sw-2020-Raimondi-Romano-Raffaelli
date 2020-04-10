package org.example;

import java.util.List;
import java.util.Map;

public class DemeterDecorator extends PlayerBuildDecorator {

    private Position firstBuildPosition;

    public DemeterDecorator(){
        String godName = "Demeter";
        super.setGodName(godName);
        String description = "Your Worker may build one additional time, but not on the same space.";
        super.setGodDescription(description);
    }

    @Override
    public boolean canBuild(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position buildPos){
        this.firstBuildPosition = buildPos;
        return super.canBuild(adjacentList, adjacentPlayerList, buildPos);
    }

    @Override
    public void activePowerAfterBuild(){
        super.setActivePower(true);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition){
        return super.canBuild(adjacentList, adjacentPlayerList, powerPosition) && !powerPosition.equals(this.firstBuildPosition);
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition){
        super.setActivePower(false);
        return new BoardChange(powerPosition, BuildType.LEVEL);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
