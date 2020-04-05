package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.board.Position;

public class ArtemisDecorator extends PlayerMoveDecorator {

    public ArtemisDecorator(PlayerInterface player){ super(player);}

    /*
    @Override
    public boolean canUsePower(Position pos) {

        return player.canMove(player.getBoard().getAdjacentCells(), pos, player.getBoard().getCell().getLevel());
    }
    */

    /*
    @Override
    public void usePower(Position pos) {
        player.moveWorker(pos, workerIndex);
    }
    */
}
