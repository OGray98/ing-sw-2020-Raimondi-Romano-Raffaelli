package org.example;

import java.util.List;
import java.util.Map;

public class DemeterDecorator extends PlayerBuildDecorator {

    private Cell firstBuildCell;

    public DemeterDecorator(){
        String godName = "Demeter";
        super.setGodName(godName);
        String description = "Your Worker may build one additional time, but not on the same space.";
        super.setGodDescription(description);
    }

    @Override
    public boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell){
        this.firstBuildCell = buildCell;
        return super.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public void activePowerAfterBuild(){
        super.setActivePower(true);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canBuild(adjacentPlayerList, adjacentList.get(0)) && !adjacentList.get(0).equals(this.firstBuildCell);
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        super.setActivePower(false);
        return new BoardChange(adjacentList.get(0).getPosition(), BuildType.LEVEL);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
