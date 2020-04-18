package it.polimi.ingsw;

import java.util.List;
import java.util.Map;

public abstract class PlayerDecorator implements PlayerInterface, CardInterface {

    private PlayerInterface player;
    private String godName;
    private String description;
    private boolean chosenGod = false;
    private final GameState currentState;
    private final GameState nextState;


    public PlayerDecorator() {}

    public PlayerInterface setPlayer(PlayerInterface player){
        this.player = player;
        return this;
    }


    @Override
    public void setStartingWorkerSituation(Cell cellOccupied, boolean cantGoUp){
         player.setStartingWorkerSituation(cellOccupied,cantGoUp);
    }

    @Override
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
    public void setCantGoUp(boolean cantGoUp){
        player.setCantGoUp(cantGoUp);
    }

    @Override
    public boolean canMove(Map<Position,PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException, NullPointerException {
        return player.canMove(adjacentPlayerList, moveCell);
    }

    @Override
    public boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException {
        return player.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public boolean hasWin() throws NullPointerException {
        return player.hasWin();
    }

    @Override
    public void move(Cell newOccupiedCell) throws NullPointerException{
        player.move(newOccupiedCell);
    }

    @Override
    public void activePowerAfterBuild(){
        player.activePowerAfterBuild();
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        return player.canUsePower(adjacentList, adjacentPlayerList);
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        return player.usePower(powerCell);
    }

    @Override
    public Cell getOldCell(){
        return player.getOldCell();
    }

    @Override
    public Cell getCellOccupied(){
        return player.getCellOccupied();
    }

    @Override
    public boolean getActivePower(){
        return player.getActivePower();
    }

    @Override
    public boolean getCantGoUp(){
        return player.getCantGoUp();
    }

    @Override
    public PlayerIndex getPlayerNum(){
        return player.getPlayerNum();
    }

    @Override
    public int getPowerListDimension(){
        return player.getPowerListDimension();
    }

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