package org.example;

import java.util.List;
import java.util.Map;

public abstract class PlayerDecorator implements PlayerInterface, CardInterface {

    private PlayerInterface player;
    private String godName;
    private String description;
    private boolean chosenGod = false;


    public PlayerDecorator() {}

    public PlayerInterface setPlayer(PlayerInterface player){
        this.player = player;
        return this;
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

    // aggiunti io
    public  void setChosenGod(Boolean condition){
        this.chosenGod = condition;
    }

    public boolean getBoolChosenGod(){
        return this.chosenGod;
    }

    @Override
    public String getGodName() {
        return this.godName;
    }

    @Override
    public String getGodDescription() {
        return this.description;
    }

    @Override
    public void setGodName(String godName) {
        this.godName = godName;
    }

    @Override
    public void setGodDescription(String description) {
        this.description = description;
    }
}
