package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.*;

import java.util.List;
import java.util.Map;

public class ZeusDecorator extends PlayerBuildDecorator{

    public ZeusDecorator() {

        super("Zeus", "Your worker may build a block under itself.", GameState.BUILD, GameState.ENDPHASE);

    }

    @Override
    public int  getPowerListDimension(){
        return 1;
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return adjacentList.get(0).getLevel() <3 && adjacentList.get(0).equals(this.getCellOccupied());
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        return new BoardChange(powerCell.getPosition(), BuildType.LEVEL);
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
