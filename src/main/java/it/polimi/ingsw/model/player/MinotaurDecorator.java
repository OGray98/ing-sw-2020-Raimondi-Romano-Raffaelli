package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;
import java.util.Map;

public class MinotaurDecorator extends PlayerMoveDecorator {

    private PlayerIndex playerOpponent;
    private Cell playerOpponentAfterPowerCell;

    public MinotaurDecorator(){
        String godName = "Minotaur";
        super.setGodName(godName);
        String description = "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level";
        super.setGodDescription(description);
    }


    @Override
    public int getPowerListDimension(){
        return 2;
    }

    /*Returns false if there is a player in the position selected, but in this case it will set activePower true */
    @Override
    public boolean canMove(Map<Position, PlayerIndex> adjacentPlayerList, Cell moveCell) throws InvalidPositionException {

        if(moveCell.getPosition().col > 4 || moveCell.getPosition().row > 4 || moveCell.getPosition().col < 0 || moveCell.getPosition().row < 0) throw new InvalidPositionException(moveCell.getPosition().row, moveCell.getPosition().col);
        if(adjacentPlayerList == null) throw new NullPointerException("adjacentPlayerList is null!");

        super.setActivePower(false);

        for(Position p : adjacentPlayerList.keySet()){
            //check if there is a player
            if ((p.equals(moveCell.getPosition())) && ((moveCell.getLevel() - super.getCellOccupied().getLevel()) <= 1)) {
                if(!(adjacentPlayerList.get(p).equals(super.getPlayerNum()))){
                    super.setActivePower(true);}
                return true;
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

        if(adjacentList.get(0).getPosition().isAdjacent(super.getCellOccupied().getPosition())){
            for(Position p : adjacentPlayerList.keySet()){
                if(adjacentList.get(0).getPosition().equals(p)){
                    this.playerOpponent = adjacentPlayerList.get(p);
                    if(adjacentList.get(1).hasDome()) return false;
                    for(Position pos : adjacentPlayerList.keySet()){
                        if(adjacentList.get(1).getPosition().equals(pos)){
                            return false;
                        }
                    }
                    this.playerOpponentAfterPowerCell = adjacentList.get(1);
                    return true;
                }
            }
        }
        if(adjacentList.get(1).getPosition().isAdjacent(super.getCellOccupied().getPosition())){
            for(Position p : adjacentPlayerList.keySet()){
                if(adjacentList.get(1).getPosition().equals(p)){
                    this.playerOpponent = adjacentPlayerList.get(p);
                    if(adjacentList.get(0).hasDome()) return false;
                    for(Position pos : adjacentPlayerList.keySet()){
                        if(adjacentList.get(0).getPosition().equals(pos)){
                            return false;
                        }
                    }
                    this.playerOpponentAfterPowerCell = adjacentList.get(0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BoardChange usePower(Cell powerCell) {
        super.setActivePower(false);
        Position opponentNewCell = this.playerOpponentAfterPowerCell.getPosition();
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum());
        boardChange.addPlayerChanges(powerCell.getPosition(),opponentNewCell,this.playerOpponent);
        return boardChange;
    }

    public void setChosenGod(Boolean condition){
        super.setChosenGod(condition);
    }


}
