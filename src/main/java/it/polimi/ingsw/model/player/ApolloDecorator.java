package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPositionException;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApolloDecorator extends PlayerMoveDecorator {

    private PlayerIndex playerOpponent;



    public ApolloDecorator() {

       super("Apollo", "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.", GameState.MOVE, GameState.CHECKWIN);

    }


    public void setChosenGod(Boolean condition) {
        super.setChosenGod(condition);
    }

    @Override
    public int getPowerListDimension() {
        return 1;
    }

    @Override
    public boolean canMoveWithPowers(Map<Position, PlayerIndex> adjacentPlayerList, List<Cell> moveCell, Cell occupiedCell, boolean cantGoUp){
        this.setStartingWorkerSituation(occupiedCell, cantGoUp);
        //if(moveCell.size() > 1) throw new IllegalArgumentException("Wrong moveCell list for this God");
        return canMove(adjacentPlayerList, moveCell.get(0)) || this.canUsePower(moveCell, adjacentPlayerList);
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

    /**
     * Implementation of Apollo power
     * The BoardChange returned contains infos to update Apollo worker and enemy worker
     * */
    @Override
    public BoardChange usePower(Cell powerCell) {
        Position startPosition = super.getCellOccupied().getPosition();
        BoardChange boardChange = new BoardChange(super.getCellOccupied().getPosition(),powerCell.getPosition(),super.getPlayerNum());
        boardChange.addPlayerChanges(powerCell.getPosition(),startPosition,this.playerOpponent);
        super.move(powerCell);
        return boardChange;

    }
}
