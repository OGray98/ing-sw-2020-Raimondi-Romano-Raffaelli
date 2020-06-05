package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.*;

import java.util.List;
import java.util.Map;

public class HestiaDecorator extends PlayerBuildDecorator{

    public HestiaDecorator() {
        super("Hestia", "Your worker may build one additional time, but this cannot be on a perimeter space.", GameState.ENDPHASE, GameState.BUILDPOWER);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canBuild(adjacentPlayerList, adjacentList.get(0)) && !adjacentList.get(0).getPosition().isPerimeterPosition();
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        return new BoardChange(powerCell.getPosition(), BuildType.LEVEL);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

    @Override
    public int getPowerListDimension(){
        return 1;
    }
}
