package org.example;

import java.util.List;
import java.util.Map;

public class PrometheusDecorator extends PlayerYourTurnDecorator {


    public PrometheusDecorator(){
        String godName = "Prometheus";
        super.setGodName(godName);
        String description = "If your Worker does not move up, it may build both before and after moving.";
        super.setGodDescription(description);
    }

    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

    @Override
    public void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp){
        super.setActivePower(true);
        super.setWorkerSituation(oldCell, cellOccupied, cantGoUp);
    }

    @Override
    public void move(Cell newOccupiedCell) throws NullPointerException {
        super.setCantGoUp(false);
        super.move(newOccupiedCell);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell) {
        return super.canBuild(adjacentPlayerList, powerCell);
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell) {
        super.setCantGoUp(true);
        super.setActivePower(false);
        return new BoardChange(powerCell.getPosition(),BuildType.LEVEL);
    }
}
