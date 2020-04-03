package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;

// Decorator that decorate the special power of Atlas
public class AtlasDecorator extends PlayerBuildDecorator {



    protected AtlasDecorator(PlayerInterface player){ super(player);}

    @Override
    public boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws InvalidPositionException, NullPointerException {
        if(super.canBuild(adjacentCells,buildingPosition)){
            //notify();
            return true;
        }
        return false;
    }

    public void buildWorker(Position pos){
        player.buildWorker(pos);
    }


    public void usePower(Position pos){
        player.getBoard().UpdateBoardBuildDome(pos);
    }


}
