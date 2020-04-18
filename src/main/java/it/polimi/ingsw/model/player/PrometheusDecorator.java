package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class PrometheusDecorator extends PlayerYourTurnDecorator {


    public PrometheusDecorator(String godName, String description, GameState powerState, GameState nextState){

        super(godName, description, powerState, nextState);
    }

    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

    @Override
    public void move(Cell newCellOccupied){
        super.setCantGoUp(false);
        super.move(newCellOccupied);
    }
    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        return super.canBuild(adjacentPlayerList, adjacentList.get(0));
    }

    @Override
    public BoardChange usePower(Cell powerCell) {
        super.setCantGoUp(true);
        return new BoardChange(powerCell.getPosition(), BuildType.LEVEL);
    }

    @Override
    public int getPowerListDimension(){
        return 1;
    }
}
