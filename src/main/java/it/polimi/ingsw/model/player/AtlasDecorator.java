package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;

import java.util.List;

public class AtlasDecorator extends PlayerBuildDecorator {

    PlayerInterface player;

    public AtlasDecorator(PlayerInterface playerinterface){
        this.player = playerinterface;
    }

    @Override
    public boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws InvalidPositionException, NullPointerException {
        if(player.canBuild(adjacentCells,buildingPosition)){
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

