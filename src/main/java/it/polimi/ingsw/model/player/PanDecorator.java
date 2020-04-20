package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class PanDecorator extends PlayerWinConditionDecorator {


    public PanDecorator(String godName, String description, GameState powerState, GameState nextState){

        super(godName, description, powerState, nextState);

    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

    /*Pan canMove() returns true only in cells where his power is not active, the other cells will return false*/
    @Override
    public boolean canMove(Map<Position,PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException {
        if(moveCell.getPosition().col > 4 || moveCell.getPosition().row > 4 || moveCell.getPosition().col < 0 || moveCell.getPosition().row < 0) throw new InvalidPositionException(moveCell.getPosition().row, moveCell.getPosition().col);
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if(p.equals(moveCell.getPosition())) return false;
        }

        if(super.getCellOccupied().getPosition().isAdjacent(moveCell.getPosition())){
            //check if there is a dome
            if (moveCell.hasDome()) return false;
            //if cantGoUp is true, check if it is a level up move
            if (super.getCantGoUp()) {
                if (moveCell.getLevel() > super.getCellOccupied().getLevel()) return false;
            }
            return moveCell.getLevel() - this.getCellOccupied().getLevel() <= 1 && moveCell.getLevel() - this.getCellOccupied().getLevel() >= (-1);
        }

        return false;
    }


    /*Pan canUsePower() return true in all the cells where his power is active*/
    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) throws InvalidPositionException{
        if(adjacentList.get(0).getPosition().col > 4 || adjacentList.get(0).getPosition().row > 4 || adjacentList.get(0).getPosition().col < 0 || adjacentList.get(0).getPosition().row < 0) throw new InvalidPositionException(adjacentList.get(0).getPosition().row, adjacentList.get(0).getPosition().col);
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if(p.equals(adjacentList.get(0).getPosition())) return false;
        }
        if(super.getCellOccupied().getPosition().isAdjacent(adjacentList.get(0).getPosition())){
            return adjacentList.get(0).getLevel() - this.getCellOccupied().getLevel() < (-1);
        }
        return false;

    }

    @Override
    public boolean hasWin() throws NullPointerException {
       if(super.getOldCell().getLevel() - super.getCellOccupied().getLevel() >= 2)
            return true;
        return super.hasWin();
    }

    @Override
    public BoardChange usePower(Cell powerCell) {
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum());
        super.move(powerCell);
        return boardChange;

    }
}
