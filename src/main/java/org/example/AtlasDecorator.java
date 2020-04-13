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
    public boolean canBuild(Map<Position,PlayerIndex> adjacentPlayerList, Cell buildCell){
        if(super.canBuild(adjacentPlayerList, buildCell)) super.setActivePower(true);
        return super.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell){
        return super.canBuild(adjacentPlayerList, powerCell);
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell){
        super.setActivePower(false);
        return new BoardChange(powerCell.getPosition(), BuildType.DOME);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
