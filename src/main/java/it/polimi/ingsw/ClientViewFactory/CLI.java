package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.utils.BuildViewMessage;
import it.polimi.ingsw.utils.MoveMessage;
import it.polimi.ingsw.utils.PlayerSelectGodMessage;
import it.polimi.ingsw.utils.PutWorkerMessage;

import java.util.List;

public class CLI extends ClientView {

    public CLI(ViewModelInterface clientModel) {
        super(clientModel);
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
    public String showSelectIP() {
        return null;
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
}
