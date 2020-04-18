package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class ApolloDecorator extends PlayerMoveDecorator {

    private PlayerIndex playerOpponent;



    public ApolloDecorator(String godName, String description, GameState powerState, GameState nextState) {

       super(godName, description, powerState, nextState);

    }


    public void setChosenGod(Boolean condition) {
        super.setChosenGod(condition);
    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }


    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        if(!super.canMove(adjacentPlayerList,adjacentList.get(0))) {
            for (Position p : adjacentPlayerList.keySet()) {
                if((p.equals(adjacentList.get(0).getPosition())) && ((adjacentList.get(0).getLevel() - super.getCellOccupied().getLevel()) <= 1)){
                    if(!(adjacentPlayerList.get(p).equals(super.getPlayerNum()))){
                        if (adjacentList.get(0).getPosition().equals(p)) {
                            this.playerOpponent = adjacentPlayerList.get(p);
                                return true;}
                    }
                }
            }
        }
        return false;
    }

    @Override
    public BoardChange usePower(Cell powerCell) {
        super.setActivePower(false);
        Position startPosition = super.getCellOccupied().getPosition();
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum());
        boardChange.addPlayerChanges(powerCell.getPosition(),startPosition,this.playerOpponent);
        return boardChange;

    }
}
