package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

/**
 * Class that decorates Player. It is used to play Triton
 * */
public class TritonDecorator extends PlayerMoveDecorator{

    public TritonDecorator() {
        super("Triton", "Each time your worker moves into a perimeter space, it may immediately move again.", GameState.BUILD, GameState.BUILD);
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        return canMove(adjacentPlayerList, adjacentList.get(0)) && super.getCellOccupied().getPosition().isPerimeterPosition();
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(), powerCell.getPosition(), super.getPlayerNum());
        super.move(powerCell);
        return boardChange;
    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
