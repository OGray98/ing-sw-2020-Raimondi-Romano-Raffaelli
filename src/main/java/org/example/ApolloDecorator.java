package org.example;

import java.util.List;
import java.util.Map;

public class ApolloDecorator extends PlayerMoveDecorator {


    public ApolloDecorator() {
        String godName = "Apollo";
        super.setGodName(godName);
        String description = "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
        super.setGodDescription(description);
    }


    public void setChosenGod(Boolean condition) {
        super.setChosenGod(condition);
    }


    @Override
    public boolean canMove(Map<Position, PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException {

        if(moveCell.getPosition().col > 4 || moveCell.getPosition().row > 4 || moveCell.getPosition().col < 0 || moveCell.getPosition().row < 0) throw new InvalidPositionException(moveCell.getPosition().row, moveCell.getPosition().col);
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if ((p.equals(moveCell)) && ((moveCell.getLevel() - super.getCellOccupied().getLevel()) <= 1)) {
                if(!adjacentPlayerList.get(moveCell).equals(super.getPlayerNum())){
                    super.setActivePower(true);}
                return false;
            }
        }

        if(super.getCellOccupied().getPosition().isAdjacent(moveCell.getPosition())){
            //check if there is a dome
            if(moveCell.hasDome()) return false;
            //if cantGoUp is true, check if it is a level up move
            if(super.getCantGoUp()){
                if(moveCell.getLevel() > super.getCellOccupied().getLevel()) return false;
            }
            return moveCell.getLevel() - super.getCellOccupied().getLevel() <= 1;
        }
        return false;
    }

    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell) {
        if(super.getActivePower())
            return true;
        return false;
    }

    @Override
    public BoardChange usePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList, Cell powerCell) {
        super.setActivePower(false);
        Position startPosition = super.getCellOccupied().getPosition();
        PlayerIndex opponent = adjacentPlayerList.get(powerCell.getPosition());
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum());
        boardChange.addPlayerChanges(powerCell.getPosition(),startPosition,opponent);
        return boardChange;

    }
}
