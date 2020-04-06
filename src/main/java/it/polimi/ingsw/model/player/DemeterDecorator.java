package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Position;

public class DemeterDecorator extends PlayerBuildDecorator {

    private Position firstBuildingPosition;

    public DemeterDecorator(PlayerInterface player){ super(player);}

    /* After the first build, Demeter player can choose if build again in an other adjacent Position
    * notify will ask to the player ( view ) is he want to use the Power */
    @Override
    public void buildWorker(Position toBuildPosition){
        player.buildWorker(toBuildPosition);
        this.firstBuildingPosition = toBuildPosition;
        //notify
    }

    /* After user has given the Position for the second building, canUsePower() check if it is a legal build action */
    @Override
    public boolean canUsePower(Position secondBuildingPosition){
        if(secondBuildingPosition == null) throw new NullPointerException("firstBuildingPosition is null!");
        return player.canBuild(secondBuildingPosition) && !(secondBuildingPosition.equals(firstBuildingPosition));
    }

    /* if canUsePower() is true, Demeter proceed to build a second time */
    @Override
    public void usePower(Position secondBuildingPosition){
        player.buildWorker(secondBuildingPosition);
    }
}
