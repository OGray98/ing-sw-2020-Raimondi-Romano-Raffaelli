package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.board.Position;

import java.util.List;

public class AthenaDecorator extends PlayerOpponentTurnDecorator {

    private List<Player> enemyPlayers;

    /* requires that otherPlayers is the list of enemy players only, not the Athena player (this) */
    public AthenaDecorator(PlayerInterface player, List<Player> otherPlayers){
        super(player);
        this.enemyPlayers = otherPlayers;
    }

    @Override
    public void moveWorker(Position newPosition){
        /* set false the attribute cantMoveUp to all players */
        for(Player p : this.enemyPlayers){
            p.setCantMoveUp(false);
        }
        player.moveWorker(newPosition);
        /* check if Athena moved up in the turn, if yes set cantMoveUp attribute to other players */
        if(player.getBoard().getCell(player.getWorkerPositionOccupied(player.getSelectedWorker())).getLevel() >
                player.getBoard().getCell(player.getWorker(player.getSelectedWorker()).getOldPosition()).getLevel()){
            for(Player p : this.enemyPlayers){
                p.setCantMoveUp(true);
            }
        }
    }

    //when athena loses, her effect must disappear
    @Override
    public List<Integer> blockedWorkers(){
        if (player.blockedWorkers().size() == 2){
            for(Player p : this.enemyPlayers){
                p.setCantMoveUp(false);
            }
        }
        return player.blockedWorkers();
    }

    // ( not sure it is a lose condition! )when athena loses, her effect must disappear
    /*@Override
    public boolean isBlockedBuilding(){
        if(player.isBlockedBuilding()){
            for(Player p : this.enemyPlayers){
                p.setCantMoveUp(false);
            }
        }
        return player.isBlockedBuilding();
    }*/
}
