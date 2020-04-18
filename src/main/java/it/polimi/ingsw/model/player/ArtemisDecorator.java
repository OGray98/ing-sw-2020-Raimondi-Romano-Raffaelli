package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class ArtemisDecorator extends PlayerMoveDecorator {

    public ArtemisDecorator(String godName, String description, GameState powerState, GameState nextState){

        super(godName, description, powerState, nextState);
    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }



    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canMove(adjacentPlayerList, adjacentList.get(0)) && !adjacentList.get(0).equals(super.getOldCell());
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(), powerCell.getPosition(), super.getPlayerNum());
        super.move(powerCell);
        return boardChange;
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
