package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

public class ArtemisDecorator extends PlayerMoveDecorator {

    private God godName;
    private String godDescription;

    public ArtemisDecorator(){
        this.godName = God.ARTEMIS;
        this.godDescription = God.ARTEMIS.GetGodDescription();
    }

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
