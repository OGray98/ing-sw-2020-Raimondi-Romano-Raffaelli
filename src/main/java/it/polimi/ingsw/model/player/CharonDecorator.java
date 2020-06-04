package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

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

        if(adjacentPlayerList.containsKey(oldEnemyPosition)){
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
        return 3;
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
