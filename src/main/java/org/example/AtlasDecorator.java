package org.example;

import java.util.List;
import java.util.Map;

public class AtlasDecorator extends PlayerBuildDecorator {


    public AtlasDecorator(){
        String godName = "Atlas";
        super.setGodName(godName);
        String description = "Your Worker may build a dome at any level.";
        super.setGodDescription(description);
    }

    @Override
    public boolean canBuild(List<Cell> adjacentList, Map<Position,PlayerIndex> adjacentPlayerList, Position buildPos){
        if(super.canBuild(adjacentList, adjacentPlayerList, buildPos)) super.setActivePower(true);
        return super.canBuild(adjacentList, adjacentPlayerList, buildPos);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition){
        return super.canBuild(adjacentList, adjacentPlayerList, powerPosition);
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition){
        super.setActivePower(false);
        return new BoardChange(powerPosition, BuildType.DOME);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
