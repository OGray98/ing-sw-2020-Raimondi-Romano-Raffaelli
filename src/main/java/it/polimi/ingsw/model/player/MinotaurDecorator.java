package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

/**
 * Class that decorates Player. It is used to play Minotaur
 * */
public class MinotaurDecorator extends PlayerMoveDecorator {

    private PlayerIndex playerOpponent;
    private Cell playerOpponentAfterPowerCell;

    public MinotaurDecorator(){

        super("Minotaur", "Our Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.", GameState.MOVE, GameState.BUILD);
    }


    @Override
    public int getPowerListDimension(){
        return 2;
    }

    @Override
    public boolean canMoveWithPowers(Map<Position, PlayerIndex> adjacentPlayerList, List<Cell> moveCell, Cell occupiedCell, boolean cantGoUp){
        this.setStartingWorkerSituation(occupiedCell, cantGoUp);

        if(moveCell.get(0).getPosition().isAdjacent(occupiedCell.getPosition())){
            return canMove(adjacentPlayerList, moveCell.get(0)) || this.canUsePower(moveCell, adjacentPlayerList);
        }

        else{
            return canMove(adjacentPlayerList, moveCell.get(1)) || this.canUsePower(moveCell, adjacentPlayerList);
        }
    }

    /*Requires List<Cell> with the cell where Minotaur want to move and the cell where the opponent player will be moved
    * Requires Map<Position, PlayerIndex> with the informations about the two cell of List<Cell>
    * canUsePower in MinotaurDecorator check if the list passed contains a cell with an opponent player
    * in that case it will save the PlayerIndex of the opponent player and the position that it will occupies after the power is used
    * Throws IllegalArgumentException when the List<Cell> passed has a size() > 2
    * Throws IllegalArgumentException when the Map<Position, PlayerIndex> passed has a size() > 2 */
    @Override
    public boolean canUsePower(List<Cell> adjacentList, Map<Position, PlayerIndex> adjacentPlayerList) {
        if(adjacentList.size() > 2) throw new IllegalArgumentException("list passed has an illegal size!");
        if(adjacentPlayerList.size() > 2) throw new IllegalArgumentException("map passed has an illegal size");

        /*This is the case when the opponent player would be put over the board, so return value is false*/
        if(adjacentList.size() <= 1) return false;

        if(!super.canMove(adjacentPlayerList,adjacentList.get(0))) {
            if (adjacentList.get(0).getPosition().isAdjacent(super.getCellOccupied().getPosition())) {
                for (Position p : adjacentPlayerList.keySet()) {
                    if (adjacentList.get(0).getPosition().equals(p) && ((adjacentList.get(0).getLevel() - super.getCellOccupied().getLevel()) <= 1)) {
                        if(!(adjacentPlayerList.get(p).equals(super.getPlayerNum()))) {
                            this.playerOpponent = adjacentPlayerList.get(p);
                            if (adjacentList.get(1).hasDome()) return false;
                            for (Position pos : adjacentPlayerList.keySet()) {
                                if (adjacentList.get(1).getPosition().equals(pos)) {
                                    return false;
                                }
                            }
                            this.playerOpponentAfterPowerCell = adjacentList.get(1);
                            return true;
                        }
                    }
                }
            }
        }

        if(!super.canMove(adjacentPlayerList,adjacentList.get(1))) {
            if (adjacentList.get(1).getPosition().isAdjacent(super.getCellOccupied().getPosition())) {
                for (Position p : adjacentPlayerList.keySet()) {
                    if (adjacentList.get(1).getPosition().equals(p) && ((adjacentList.get(1).getLevel() - super.getCellOccupied().getLevel()) <= 1)) {
                        if(!(adjacentPlayerList.get(p).equals(super.getPlayerNum()))) {
                            this.playerOpponent = adjacentPlayerList.get(p);
                            if (adjacentList.get(0).hasDome()) return false;
                            for (Position pos : adjacentPlayerList.keySet()) {
                                if (adjacentList.get(0).getPosition().equals(pos)) {
                                    return false;
                                }
                            }
                            this.playerOpponentAfterPowerCell = adjacentList.get(0);
                            return true;
                            }
                        }
                    }
                }
            }

        return false;
    }

    @Override
    public BoardChange usePower(Cell powerCell) {

        Position opponentNewCell = this.playerOpponentAfterPowerCell.getPosition();
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum());
        boardChange.addPlayerChanges(powerCell.getPosition(),opponentNewCell,this.playerOpponent);
        super.move(powerCell);
        return boardChange;
    }

    @Override
    public Position getSecondPowerPosition(Position firsPowerPosition){
        int diffRow = firsPowerPosition.row - getCellOccupied().getPosition().row;
        int diffCol = firsPowerPosition.col - getCellOccupied().getPosition().col;
        int newRow = firsPowerPosition.row + diffRow;
        int newCol = firsPowerPosition.col + diffCol;
        if (!(newRow < 0 || newRow > 4 || newCol < 0 || newCol > 4))
            return new Position(newRow, newCol);
        throw new InvalidPositionException(newRow, newCol);
    }

    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }

}
