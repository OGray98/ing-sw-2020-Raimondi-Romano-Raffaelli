package org.example;

import java.util.List;
import java.util.Map;

public class HephaestusDecorator extends PlayerBuildDecorator {


    private Cell buildCell;

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
    public boolean canBuild( Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException {
        this.buildCell = buildCell;
        return super.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public void activePowerAfterBuild() {
        super.setActivePower(true);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell) {
        if(super.canBuild(adjacentPlayerList, powerCell) && this.buildCell == powerCell)
            return true;
        return false;
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell) {
        super.setActivePower(false);
        return new BoardChange(powerCell.getPosition(),BuildType.LEVEL);
    }


}
