package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class AthenaDecorator extends PlayerOpponentTurnDecorator {


    public AthenaDecorator(){
        String godName = "Athena";
        super.setGodName(godName);
        String description = "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
        super.setGodDescription(description);
    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }

    @Override
    public void move(Cell newOccupiedCell){
        if(newOccupiedCell.getLevel() > super.getCellOccupied().getLevel()){
            super.setActivePower(true);
        }
        super.move(newOccupiedCell);
    }

    @Override
    public boolean canBuild(Map<Position, PlayerIndex> adjacentPlayerList, Cell buildCell) throws InvalidPositionException, NullPointerException {
        super.setActivePower(false);
        return super.canBuild(adjacentPlayerList, buildCell);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return true;
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        super.setActivePower(false);
        return new BoardChange(true);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
