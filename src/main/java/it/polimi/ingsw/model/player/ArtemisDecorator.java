package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

/**
 * Class that decorates Player. It is used to play Artemis
 * */
public class ArtemisDecorator extends PlayerMoveDecorator {

    public ArtemisDecorator(){

        super("Artemis", "Your Worker may move one additional time, but not back to its initial space.", GameState.BUILD, GameState.SECOND_MOVE);
    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }



    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return super.canMove(adjacentPlayerList, adjacentList.get(0)) && !adjacentList.get(0).equals(super.getOldCell());
    }

    /**
     * Implementation of Artemis power
     * The BoardChange returned contains infos to update Artemis worker
     * */
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
