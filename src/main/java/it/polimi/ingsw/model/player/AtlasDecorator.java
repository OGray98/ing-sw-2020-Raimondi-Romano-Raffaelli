package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class AtlasDecorator extends PlayerBuildDecorator {


    public AtlasDecorator(String godName, String description, GameState powerState, GameState nextState){

        super(godName, description, powerState, nextState);

    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }


    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canBuild(adjacentPlayerList, adjacentList.get(0));
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        return new BoardChange(powerCell.getPosition(), BuildType.DOME);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
