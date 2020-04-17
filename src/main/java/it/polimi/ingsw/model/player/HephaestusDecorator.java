package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidIncrementLevelException;

import it.polimi.ingsw.exceptions.NotSamePositionException;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

// Decorator that decorate the special power of Hephaestus
public class HephaestusDecorator extends PlayerBuildDecorator {

    private Position position;
    private God godName;
    private String godDescription;


    public HephaestusDecorator(){
        this.godName = God.HEPHAESTUS;
        this.godDescription = God.HEPHAESTUS.GetGodDescription();
    }

    public HephaestusDecorator(PlayerInterface player){
        super(player);
    }



    @Override
    public void buildWorker(Position toBuildPosition) {
        player.buildWorker(toBuildPosition);
        this.position = toBuildPosition;
        //notify();
    }

    @Override
    public boolean canUsePower(Position pos) throws InvalidIncrementLevelException {
        if(player.getBoard().getCell(pos).getLevel() <= 3 && this.position == pos)
            return true;
        else if(player.getBoard().getCell(pos).getOccupation() == CellOccupation.DOME)
            throw new InvalidIncrementLevelException(pos.row,pos.col);
        else if(this.position != pos)
            throw new NotSamePositionException(pos.row,pos.col);
        return false;
    }

    @Override
    public void usePower(Position pos) {
        player.buildWorker(pos);
    }

    @Override
    public God getGodName() {
        return this.godName;
    }

    @Override
    public String getGodDescription() {
        return this.godDescription;
    }
}
