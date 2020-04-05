package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.board.Position;

public class ArtemisDecorator extends PlayerMoveDecorator {

    public ArtemisDecorator(PlayerInterface player){ super(player);}


    @Override
    public boolean canUsePower(Position pos) throws NullPointerException, InvalidPositionException {
        if(pos == null) throw new NullPointerException("pos");
        if(pos.isIllegal()) throw new InvalidPositionException(pos.row, pos.col);
        return player.canMove(pos) && !player.getWorker(player.getSelectedWorker()).getOldPosition().equals(pos);
    }

    @Override
    public void usePower(Position pos) {
        if(pos == null) throw new NullPointerException("pos");
        if(pos.isIllegal()) throw new InvalidPositionException(pos.row, pos.col);
        player.moveWorker(pos);
        //TODO: Controllo vincitanel controller
    }
}
