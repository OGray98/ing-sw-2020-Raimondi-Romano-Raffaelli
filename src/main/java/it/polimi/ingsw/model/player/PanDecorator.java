package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.NotSelectedWorkerException;
import it.polimi.ingsw.model.deck.God;

public class PanDecorator extends PlayerWinConditionDecorator {


    private God godName;
    private String godDescription;

    public PanDecorator(){
        this.godName = God.PAN;
        this.godDescription = God.PAN.GetGodDescription();
    }

    public PanDecorator(PlayerInterface player) {
        super(player);
    }

    @Override
    public boolean hasWin() throws NullPointerException, NotSelectedWorkerException {
        //Pan check
        if (player.getBoard().getCell(player.getWorker(getSelectedWorker()).getOldPosition()).getLevel() -
                player.getBoard().getCell(player.getWorker(getSelectedWorker()).getPositionOccupied()).getLevel()
                >= 2)
            return true;
        //Normal check
        return player.hasWin();
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
