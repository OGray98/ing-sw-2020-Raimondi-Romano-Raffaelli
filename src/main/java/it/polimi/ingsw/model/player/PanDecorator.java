package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.NotSelectedWorkerException;

public class PanDecorator extends PlayerWinConditionDecorator {

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
}
