package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

/**
 * Class that decorates Player. It is used to play Athena
 * */
public class AthenaDecorator extends PlayerOpponentTurnDecorator {


    public AthenaDecorator(){

        super("Athena", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", GameState.MOVE, GameState.BUILD);

    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }

    @Override
    public boolean canMoveWithPowers(Map<Position, PlayerIndex> adjacentPlayerList, List<Cell> moveCell, Cell occupiedCell, boolean cantGoUp){
        this.setStartingWorkerSituation(occupiedCell, cantGoUp);

        return canMove(adjacentPlayerList, moveCell.get(0)) || this.canUsePower(moveCell, adjacentPlayerList);
    }

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
            return moveCell.getLevel() - super.getCellOccupied().getLevel() <= 0;
        }

        return false;
    }



    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList){
        if(adjacentList.get(0).getPosition().col > 4 || adjacentList.get(0).getPosition().row > 4 || adjacentList.get(0).getPosition().col < 0 || adjacentList.get(0).getPosition().row < 0) throw new InvalidPositionException(adjacentList.get(0).getPosition().row, adjacentList.get(0).getPosition().col);
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if(p.equals(adjacentList.get(0).getPosition())) return false;
        }

        if(super.getCellOccupied().getPosition().isAdjacent(adjacentList.get(0).getPosition())){
            //check if there is a dome
            if (adjacentList.get(0).hasDome()) return false;
            //if cantGoUp is true, check if it is a level up move

            return (adjacentList.get(0).getLevel() - super.getCellOccupied().getLevel()) == 1;
        }

        return false;
    }

    @Override
    public BoardChange usePower(Cell powerCell){
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum(),true);
        super.move(powerCell);
        return boardChange;
    }

    @Override
    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }
}
