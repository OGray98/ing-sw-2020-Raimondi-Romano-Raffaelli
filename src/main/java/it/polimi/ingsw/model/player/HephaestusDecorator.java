package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidIncrementLevelException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;

// Decorator that decorate the special power of Hephaestus
public class HephaestusDecorator extends PlayerBuildDecorator {



    public HephaestusDecorator(PlayerInterface player){
        super(player);
    }

    @Override
    public boolean canBuild(Position buildingPosition) throws InvalidPositionException, NullPointerException {
        if(super.canBuild(buildingPosition)){
            //notify();
            return true;
        }
        return false;
    }

    @Override
    public void buildWorker(Position toBuildPosition) {
        player.buildWorker(toBuildPosition);
        //notify();
    }

    @Override
    public void usePower(Position pos) throws InvalidIncrementLevelException {
        if(player.getBoard().getCell(pos).getOccupation() == CellOccupation.DOME)
            throw new InvalidIncrementLevelException(pos.row,pos.col);
        player.buildWorker(pos);
    }
}
