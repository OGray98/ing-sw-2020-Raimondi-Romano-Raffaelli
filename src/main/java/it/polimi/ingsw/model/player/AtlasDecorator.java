package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

// Decorator that decorate the special power of Atlas
public class AtlasDecorator extends PlayerBuildDecorator {

    private God godName;
    private String godDescription;

    public AtlasDecorator(){
        this.godName = God.ATLAS;
        this.godDescription = God.ATLAS.GetGodDescription();
    }

    protected AtlasDecorator(PlayerInterface player){ super(player);}

    @Override
    public boolean canBuild(Position buildingPosition) throws NullPointerException {
        if(super.canBuild(buildingPosition)){
            //notify();
            return true;
        }
        return false;
    }

    @Override
    //Used if can build is true and user doesn't use the power
    public void buildWorker(Position pos){
        player.buildWorker(pos);
    }

    @Override
    //Used if can build is true and user use the power
    public void usePower(Position pos){
        player.getBoard().UpdateBoardBuildDome(pos);
    }

    @Override
    public God getGodName() {
        return this.godName;
    }

    @Override
    public String getGodDescription(){
        return this.godDescription;
    }
}

