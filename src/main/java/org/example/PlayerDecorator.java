package org.example;

import java.util.List;
import java.util.Map;

public abstract class PlayerDecorator implements PlayerInterface {

    private PlayerInterface player;
    private String name;
    private String description;

    public PlayerDecorator(){}
    public PlayerDecorator(PlayerInterface player) {
        this.player = player;
    }

    public void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp){
         player.setStartingWorkerSituation(cellOccupied,cantGoUp);
    }

    public void setWorkerSituation(Cell oldCell, Cell cellOccupied, boolean cantGoUp){
        player.setWorkerSituation(oldCell,cellOccupied,cantGoUp);
    }

    @Override
    public void setAfterMove(Cell oldCell, Cell cellOccupied) {
        player.setAfterMove(oldCell, cellOccupied);
    }

    @Override
    public void setActivePower(boolean isPowerOn) {
        player.setActivePower(isPowerOn);
    }

    @Override
    public boolean canMove(List<Cell> adjacentCells, Map<Position, PlayerIndex> adjacentPlayerList, Position movePos) throws InvalidPositionException, NullPointerException {
        return player.canMove(adjacentCells, adjacentPlayerList, movePos);
    }

    @Override
    public boolean canBuild(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Position buildPos) throws InvalidPositionException, NullPointerException {
        return player.canBuild(adjacentList, adjacentPlayerList, buildPos);
    }

    @Override
    public boolean hasWin() throws NullPointerException {
        return player.hasWin();
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        return player.canUsePower(adjacentList, adjacentPlayerList);
    }

    public String getDescription(){
        return this.description;
    }
}
