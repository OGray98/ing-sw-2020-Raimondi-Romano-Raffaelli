package org.example;

import java.util.List;
import java.util.Map;

public class HephaestusDecorator extends PlayerBuildDecorator {


    private Position buildPosition;

    public HephaestusDecorator(){
        String godName = "Hephaestus";
        super.setGodName(godName);
        String description = "Your Worker may build one additional block (not dome) on top of your first block.";
        super.setGodDescription(description);
    }




    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }


    @Override
    public boolean canBuild(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position buildPos) throws InvalidPositionException, NullPointerException {
        this.buildPosition = buildPos;
        return super.canBuild(adjacentList, adjacentPlayerList, buildPos);
    }

    @Override
    public void activePowerAfterBuild() {
        super.setActivePower(true);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition) {
        if(super.canBuild(adjacentList, adjacentPlayerList, powerPosition) && this.buildPosition == powerPosition)
            return true;
        return false;
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position powerPosition) {
        super.setActivePower(false);
        return new BoardChange(powerPosition,BuildType.LEVEL);
    }


}
