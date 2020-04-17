package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

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
    public void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp) {
        super.setActivePower(false);
        super.setStartingWorkerSituation(cellOccupied, cantGoUp);
    }

    @Override
    public void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp){
        super.setActivePower(false);
        super.setWorkerSituation(oldCell, cellOccupied, cantGoUp);
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
    public BoardChange usePower(Cell powerCell){
        super.setActivePower(false);
        return new BoardChange(powerCell.getPosition(), BuildType.LEVEL);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

    @Override
    public int getPowerListDimension(){
        return 1;
    }


}

