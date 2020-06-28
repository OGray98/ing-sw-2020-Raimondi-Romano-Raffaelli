package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

/**
 * Class that decorates Player. It is used to play Charon
 * */
public class CharonDecorator extends PlayerYourTurnDecorator{

    private PlayerIndex enemyIndex;
    private Position newEnemyPosition;

    public CharonDecorator() {
        super("Charon", "Before your worker moves, you may force a neighboring opponent worker to the space directly on the other side of your worker, if that space is unoccupied.", GameState.MOVE, GameState.INITPOWER);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){

        if(adjacentList.size() <= 1)
            return false;

        if(adjacentPlayerList.size() > 1)
            return false;

        Position oldEnemyPosition = adjacentList.get(0).getPosition();

        if(adjacentPlayerList.containsKey(oldEnemyPosition) && adjacentPlayerList.get(oldEnemyPosition) != super.getPlayerNum()){
            if(!adjacentList.get(1).hasDome()){
                this.enemyIndex = adjacentPlayerList.get(oldEnemyPosition);
                newEnemyPosition = adjacentList.get(1).getPosition();
                return true;
            }
        }
        return false;
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        return new BoardChange(powerCell.getPosition(), newEnemyPosition, enemyIndex);
    }
    @Override
    public int getPowerListDimension(){
        return 2;
    }

    @Override
    public Position getSecondPowerPosition(Position firstPowerPos){
        int diffRow = firstPowerPos.row - getCellOccupied().getPosition().row;
        int diffCol = firstPowerPos.col - getCellOccupied().getPosition().col;
        int newRow = getCellOccupied().getPosition().row - (diffRow);
        int newCol = getCellOccupied().getPosition().col - (diffCol);
        if (!(newRow < 0 || newRow > 4 || newCol < 0 || newCol > 4))
            return new Position(newRow, newCol);
        throw new InvalidPositionException(newRow, newCol);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
