package it.polimi.ingsw.stub;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

import java.util.List;

public class StubView extends ClientView {

    public StubView(PlayerIndex ind, ViewModelInterface model) {
        super(model);
        super.setPlayer(ind);
    }

    @Override
    public void updatePutWorker(PutWorkerMessage message) {

    }

    @Override
    public void updateMoveWorker(MoveMessage message) {

    }

    @Override
    public void updateBuild(BuildViewMessage message) {

    }


    @Override
    public void updateSelectedCardView(PlayerSelectGodMessage message) {

    }

    @Override
    public void updateActions(PositionMessage message) {

    }

    @Override
    public void receiveErrorMessage(String error) {

    }

    @Override
    public void init() {

    }

    @Override
    public void showGod(List<String> gods) {

    }

    @Override
    public String showSelectIP(String message) {
        return null;
    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void removeActionsFromView(){

    }
}
